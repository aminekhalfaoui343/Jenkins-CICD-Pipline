package com.visualpathit.account.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComponentsTest {
    @Test
    public void storesServiceConfiguration() {
        Components components = new Components();
        components.setActiveHost("cache-active");
        components.setActivePort("11211");
        components.setStandByHost("cache-standby");
        components.setStandByPort("11212");
        components.setRabbitMqHost("rabbit");
        components.setRabbitMqPort("5672");
        components.setRabbitMqUser("user");
        components.setRabbitMqPassword("password");
        components.setElasticsearchHost("search");
        components.setElasticsearchPort("9200");
        components.setElasticsearchCluster("cluster");
        components.setElasticsearchNode("node");

        assertEquals("cache-active", components.getActiveHost());
        assertEquals("11211", components.getActivePort());
        assertEquals("cache-standby", components.getStandByHost());
        assertEquals("11212", components.getStandByPort());
        assertEquals("rabbit", components.getRabbitMqHost());
        assertEquals("5672", components.getRabbitMqPort());
        assertEquals("user", components.getRabbitMqUser());
        assertEquals("password", components.getRabbitMqPassword());
        assertEquals("search", components.getElasticsearchHost());
        assertEquals("9200", components.getElasticsearchPort());
        assertEquals("cluster", components.getElasticsearchCluster());
        assertEquals("node", components.getElasticsearchNode());
    }
}
