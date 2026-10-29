package com.visualpathit.account.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.junit.Test;
import org.mockito.MockedConstruction;

import com.visualpathit.account.beans.Components;
import com.visualpathit.account.model.User;

public class MemcachedUtilsTest {
    @Test
    public void returnsFailureWhenCacheConfigurationIsMissing() {
        MemcachedUtils utility = new MemcachedUtils(new Components());

        assertEquals(
                MemcachedUtils.MEMCACHED_CONNECTION_FAILURE,
                utility.memcachedSetData(new User(), "key"));
        assertNull(utility.memcachedGetData("key"));
        assertNull(utility.memcachedConnection());
        assertNull(utility.standByMemcachedConn());
    }

    @Test
    public void writesAndReadsUsersWhenCacheIsHealthy() throws Exception {
        Components components = configuredComponents();
        User user = new User();
        OperationFuture<Boolean> writeResult = mock(OperationFuture.class);
        when(writeResult.get()).thenReturn(true);
        Map<SocketAddress, Map<String, String>> stats = Map.of(
                new InetSocketAddress("localhost", 11211),
                Map.of("pid", "123"));

        try (MockedConstruction<MemcachedClient> clients =
                     mockConstruction(
                             MemcachedClient.class, (client, context) -> {
                                 when(client.getStats()).thenReturn(stats);
                                 when(client.set("key", 900, user))
                                         .thenReturn(writeResult);
                                 when(client.get("key")).thenReturn(user);
                             })) {
            MemcachedUtils utility = new MemcachedUtils(components);

            assertEquals(
                    " Data is From DB and Data Inserted In Cache !!",
                    utility.memcachedSetData(user, "key"));
            assertSame(user, utility.memcachedGetData("key"));
            assertEquals(2, clients.constructed().size());
        }
    }

    @Test
    public void restoresInterruptFlagWhenCacheWriteIsInterrupted()
            throws Exception {
        User user = new User();
        OperationFuture<Boolean> writeResult = mock(OperationFuture.class);
        when(writeResult.get()).thenThrow(new InterruptedException("stop"));
        Map<SocketAddress, Map<String, String>> stats = Map.of(
                new InetSocketAddress("localhost", 11211),
                Map.of("pid", "123"));

        try (MockedConstruction<MemcachedClient> clients =
                     mockConstruction(
                             MemcachedClient.class, (client, context) -> {
                                 when(client.getStats()).thenReturn(stats);
                                 when(client.set("key", 900, user))
                                         .thenReturn(writeResult);
                             })) {
            MemcachedUtils utility =
                    new MemcachedUtils(configuredComponents());

            assertEquals(
                    MemcachedUtils.MEMCACHED_CONNECTION_FAILURE,
                    utility.memcachedSetData(user, "key"));
            assertTrue(Thread.interrupted());
            assertEquals(1, clients.constructed().size());
        }
    }

    @Test
    public void rejectsInvalidInputsBeforeConnecting() {
        MemcachedUtils utility =
                new MemcachedUtils(configuredComponents());

        assertEquals(
                MemcachedUtils.MEMCACHED_CONNECTION_FAILURE,
                utility.memcachedSetData(null, "key"));
        assertNull(utility.memcachedGetData(" "));
    }

    private static Components configuredComponents() {
        Components components = new Components();
        components.setActiveHost("localhost");
        components.setActivePort("11211");
        components.setStandByHost("localhost");
        components.setStandByPort("11212");
        return components;
    }
}
