package com.visualpathit.account.utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visualpathit.account.beans.Components;
import com.visualpathit.account.model.User;

@Service
public final class MemcachedUtils {
    /** Cache entry lifetime in seconds. */
    private static final int CACHE_EXPIRATION_SECONDS = 900;
    /** Memcached stat key used to identify a healthy server. */
    private static final String SERVER_ID_STAT = "pid";
    /** Status returned when a cache connection or write fails. */
    public static final String MEMCACHED_CONNECTION_FAILURE =
            "Memcached Connection Failure !!";
    /** Logger for cache availability and operation errors. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(MemcachedUtils.class);

    /** Application configuration for cache endpoints. */
    private final Components components;

    /**
     * Creates the cache utility with injected application configuration.
     *
     * @param configuration configuration containing cache endpoints
     */
    public MemcachedUtils(final Components configuration) {
        this.components = configuration;
    }

    /**
     * Stores a user in the cache when a cache server is available.
     *
     * @param user user to cache
     * @param key cache key
     * @return existing cache operation status message
     */
    public String memcachedSetData(
            final User user, final String key) {
        if (user == null || !StringUtils.hasText(key)) {
            return MEMCACHED_CONNECTION_FAILURE;
        }

        MemcachedClient client = null;
        try {
            client = memcachedConnection();
            if (client == null) {
                return MEMCACHED_CONNECTION_FAILURE;
            }
            Future<Boolean> result = client.set(
                    key, CACHE_EXPIRATION_SECONDS, user);
            return Boolean.TRUE.equals(result.get())
                    ? " Data is From DB and Data Inserted In Cache !!"
                    : MEMCACHED_CONNECTION_FAILURE;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Unable to store user data in Memcached", exception);
            return MEMCACHED_CONNECTION_FAILURE;
        } catch (Exception exception) {
            LOGGER.warn("Unable to store user data in Memcached", exception);
            return MEMCACHED_CONNECTION_FAILURE;
        } finally {
            shutdown(client);
        }
    }

    /**
     * Retrieves a cached user when available.
     *
     * @param key cache key
     * @return cached user or {@code null} if unavailable
     */
    public User memcachedGetData(final String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }

        MemcachedClient client = null;
        try {
            client = memcachedConnection();
            if (client == null) {
                return null;
            }
            Object cachedValue = client.get(key);
            return cachedValue instanceof User user ? user : null;
        } catch (Exception exception) {
            LOGGER.warn(
                    "Unable to retrieve user data from Memcached", exception);
            return null;
        } finally {
            shutdown(client);
        }
    }

    /**
     * Connects to the active cache and falls back to the standby server.
     *
     * @return connected cache client, or {@code null} if neither server is
     *         available
     */
    public MemcachedClient memcachedConnection() {
        MemcachedClient activeClient = connect(
                components.getActiveHost(), components.getActivePort());
        return activeClient != null
                ? activeClient
                : connect(
                        components.getStandByHost(),
                        components.getStandByPort());
    }

    /**
     * Connects to the configured standby cache server.
     *
     * @return standby cache client, or {@code null} if unavailable
     */
    public MemcachedClient standByMemcachedConn() {
        return connect(
                components.getStandByHost(), components.getStandByPort());
    }

    private MemcachedClient connect(
            final String host, final String port) {
        if (!StringUtils.hasText(host) || !StringUtils.hasText(port)) {
            return null;
        }

        MemcachedClient client = null;
        try {
            client = new MemcachedClient(
                    new InetSocketAddress(host, Integer.parseInt(port)));
            if (hasServerId(client.getStats())) {
                return client;
            }
            LOGGER.warn(
                    "Memcached server {} did not return its health statistics",
                    host);
        } catch (Exception exception) {
            LOGGER.warn(
                    "Unable to connect to Memcached server {}",
                    host, exception);
        }
        shutdown(client);
        return null;
    }

    private boolean hasServerId(
            final Map<SocketAddress, Map<String, String>> stats) {
        if (stats == null) {
            return false;
        }
        for (Map<String, String> serverStats : stats.values()) {
            if (serverStats != null
                    && StringUtils.hasText(
                            serverStats.get(SERVER_ID_STAT))) {
                return true;
            }
        }
        return false;
    }

    private void shutdown(final MemcachedClient client) {
        if (client != null) {
            try {
                client.shutdown();
            } catch (RuntimeException exception) {
                LOGGER.warn(
                        "Unable to shut down Memcached client cleanly",
                        exception);
            }
        }
    }
}
