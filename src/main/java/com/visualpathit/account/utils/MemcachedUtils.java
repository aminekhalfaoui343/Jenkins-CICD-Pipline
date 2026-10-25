package com.visualpathit.account.utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visualpathit.account.beans.Components;
import com.visualpathit.account.model.User;

@Service
public class MemcachedUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedUtils.class);
    private static final String SERVER_ID_STAT = "pid";

    private static Components object;

    @Autowired
    public void setComponents(Components components) {
        MemcachedUtils.object = components;
    }

    public static String memcachedSetData(User user, String key) {
        if (user == null || !StringUtils.hasText(key)) {
            return "Memcached Connection Failure !!";
        }

        MemcachedClient client = null;
        try {
            client = memcachedConnection();
            if (client == null) {
                return "Memcached Connection Failure !!";
            }
            Future<Boolean> result = client.set(key, 900, user);
            return Boolean.TRUE.equals(result.get())
                    ? " Data is From DB and Data Inserted In Cache !!"
                    : "Memcached Connection Failure !!";
        } catch (Exception exception) {
            LOGGER.warn("Unable to store user data in Memcached", exception);
            return "Memcached Connection Failure !!";
        } finally {
            shutdown(client);
        }
    }

    public static User memcachedGetData(String key) {
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
            LOGGER.warn("Unable to retrieve user data from Memcached", exception);
            return null;
        } finally {
            shutdown(client);
        }
    }

    public static MemcachedClient memcachedConnection() {
        if (object == null) {
            LOGGER.warn("Memcached configuration is not initialized");
            return null;
        }

        MemcachedClient activeClient = connect(object.getActiveHost(), object.getActivePort());
        return activeClient != null
                ? activeClient
                : connect(object.getStandByHost(), object.getStandByPort());
    }

    public static MemcachedClient standByMemcachedConn() {
        if (object == null) {
            LOGGER.warn("Memcached configuration is not initialized");
            return null;
        }
        return connect(object.getStandByHost(), object.getStandByPort());
    }

    private static MemcachedClient connect(String host, String port) {
        if (!StringUtils.hasText(host) || !StringUtils.hasText(port)) {
            return null;
        }

        MemcachedClient client = null;
        try {
            client = new MemcachedClient(new InetSocketAddress(host, Integer.parseInt(port)));
            if (hasServerId(client.getStats())) {
                return client;
            }
            LOGGER.warn("Memcached server {} did not return its health statistics", host);
        } catch (Exception exception) {
            LOGGER.warn("Unable to connect to Memcached server {}", host, exception);
        }
        shutdown(client);
        return null;
    }

    private static boolean hasServerId(Map<SocketAddress, Map<String, String>> stats) {
        if (stats == null) {
            return false;
        }
        for (Map<String, String> serverStats : stats.values()) {
            if (serverStats != null && StringUtils.hasText(serverStats.get(SERVER_ID_STAT))) {
                return true;
            }
        }
        return false;
    }

    private static void shutdown(MemcachedClient client) {
        if (client != null) {
            try {
                client.shutdown();
            } catch (RuntimeException exception) {
                LOGGER.warn("Unable to shut down Memcached client cleanly", exception);
            }
        }
    }
}
