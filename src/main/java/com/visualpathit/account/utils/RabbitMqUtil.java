package com.visualpathit.account.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visualpathit.account.beans.Components;

/**
 * Provides access to RabbitMQ connection settings.
 */
@Service
public final class RabbitMqUtil {
    /** Injected application configuration. */
    private static Components components;

    /**
     * Sets the injected application configuration.
     *
     * @param configuration application configuration bean
     */
    @Autowired
    public void setComponents(final Components configuration) {
        RabbitMqUtil.components = configuration;
    }

    /**
     * Returns the configured RabbitMQ host.
     *
     * @return RabbitMQ host
     */
    public static String getRabbitMqHost() {
        return components.getRabbitMqHost();
    }

    /**
     * Returns the configured RabbitMQ port.
     *
     * @return RabbitMQ port
     */
    public static String getRabbitMqPort() {
        return components.getRabbitMqPort();
    }

    /**
     * Returns the configured RabbitMQ username.
     *
     * @return RabbitMQ username
     */
    public static String getRabbitMqUser() {
        return components.getRabbitMqUser();
    }

    /**
     * Returns the configured RabbitMQ password.
     *
     * @return RabbitMQ password
     */
    public static String getRabbitMqPassword() {
        return components.getRabbitMqPassword();
    }
}
