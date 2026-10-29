package com.visualpathit.account.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedConstruction;

import com.visualpathit.account.beans.Components;
import com.visualpathit.account.utils.RabbitMqUtil;

public class ProducerServiceImplTest {
    @Test
    public void publishesMessageToFanoutExchange() throws Exception {
        Components components = configuredComponents();
        Connection connection = mock(Connection.class);
        Channel channel = mock(Channel.class);
        when(connection.createChannel()).thenReturn(channel);
        ProducerServiceImpl producer = producer(components);

        try (MockedConstruction<ConnectionFactory> factories =
                     mockConstruction(
                             ConnectionFactory.class, (factory, context) ->
                                     when(factory.newConnection())
                                             .thenReturn(connection))) {
            assertEquals("response", producer.produceMessage("payload"));

            verify(channel).exchangeDeclare("messages", "fanout");
            verify(channel).basicPublish(
                    org.mockito.ArgumentMatchers.eq("messages"),
                    org.mockito.ArgumentMatchers.eq(""),
                    isNull(),
                    ArgumentMatchers.argThat(
                            body -> Arrays.equals(
                                    body, "payload".getBytes(
                                            java.nio.charset.StandardCharsets
                                                    .UTF_8))));
            assertEquals(1, factories.constructed().size());
        }
    }

    @Test
    public void returnsResponseAfterConnectionFailure() throws Exception {
        ProducerServiceImpl producer = producer(configuredComponents());

        try (MockedConstruction<ConnectionFactory> factories =
                     mockConstruction(
                             ConnectionFactory.class, (factory, context) ->
                                     when(factory.newConnection())
                                             .thenThrow(
                                                     new IOException("offline")))) {
            assertEquals("response", producer.produceMessage("payload"));
            assertEquals(1, factories.constructed().size());
        }
    }

    @Test
    public void returnsResponseAfterConnectionTimeout() throws Exception {
        ProducerServiceImpl producer = producer(configuredComponents());

        try (MockedConstruction<ConnectionFactory> factories =
                     mockConstruction(
                             ConnectionFactory.class, (factory, context) ->
                                     when(factory.newConnection())
                                             .thenThrow(
                                                     new TimeoutException(
                                                             "timeout")))) {
            assertEquals("response", producer.produceMessage("payload"));
            assertEquals(1, factories.constructed().size());
        }
    }

    @Test
    public void rejectsNullMessage() {
        ProducerServiceImpl producer = producer(configuredComponents());

        assertThrows(
                NullPointerException.class,
                () -> producer.produceMessage(null));
    }

    private static ProducerServiceImpl producer(
            final Components components) {
        return new ProducerServiceImpl(new RabbitMqUtil(components));
    }

    private static Components configuredComponents() {
        Components components = new Components();
        components.setRabbitMqHost("localhost");
        components.setRabbitMqPort("5672");
        components.setRabbitMqUser("user");
        components.setRabbitMqPassword("password");
        return components;
    }
}
