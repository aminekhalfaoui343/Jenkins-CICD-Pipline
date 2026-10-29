package com.visualpathit.account.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.mockito.MockedConstruction;

import com.visualpathit.account.beans.Components;
import com.visualpathit.account.utils.RabbitMqUtil;

public class RabbitMqControllerTest {
    @Test
    public void returnsErrorViewWhenBrokerPortIsInvalid() {
        Components components = new Components();
        components.setRabbitMqHost("localhost");
        components.setRabbitMqPort("not-a-port");
        components.setRabbitMqUser("user");
        components.setRabbitMqPassword("password");
        RabbitMqController controller =
                new RabbitMqController(new RabbitMqUtil(components));

        assertEquals(
                "rabbitmq-error",
                controller.checkRabbitMqStatus().getViewName());
    }

    @Test
    public void returnsSuccessViewWhenBrokerConnectionIsOpen()
            throws Exception {
        Components components = configuredComponents();
        Connection connection = mock(Connection.class);
        when(connection.isOpen()).thenReturn(true);
        RabbitMqController controller =
                new RabbitMqController(new RabbitMqUtil(components));

        try (MockedConstruction<ConnectionFactory> factories =
                     mockConstruction(
                             ConnectionFactory.class, (factory, context) ->
                                     when(factory.newConnection())
                                             .thenReturn(connection))) {
            assertEquals(
                    "rabbitmq",
                    controller.checkRabbitMqStatus().getViewName());
            assertEquals(1, factories.constructed().size());
        }
    }

    @Test
    public void returnsErrorViewWhenBrokerConnectionFails() throws Exception {
        Components components = configuredComponents();
        RabbitMqController controller =
                new RabbitMqController(new RabbitMqUtil(components));

        try (MockedConstruction<ConnectionFactory> factories =
                     mockConstruction(
                             ConnectionFactory.class, (factory, context) ->
                                     when(factory.newConnection())
                                             .thenThrow(
                                                     new java.io.IOException(
                                                             "offline")))) {
            assertEquals(
                    "rabbitmq-error",
                    controller.checkRabbitMqStatus().getViewName());
            assertEquals(1, factories.constructed().size());
        }
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
