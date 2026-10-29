package com.visualpathit.account.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.visualpathit.account.beans.Components;

public class RabbitMqUtilTest {
    @Test
    public void exposesConfiguredRabbitMqSettings() {
        Components components = new Components();
        components.setRabbitMqHost("rabbit");
        components.setRabbitMqPort("5672");
        components.setRabbitMqUser("user");
        components.setRabbitMqPassword("password");

        RabbitMqUtil utility = new RabbitMqUtil(components);

        assertEquals("rabbit", utility.getRabbitMqHost());
        assertEquals("5672", utility.getRabbitMqPort());
        assertEquals("user", utility.getRabbitMqUser());
        assertEquals("password", utility.getRabbitMqPassword());
    }
}
