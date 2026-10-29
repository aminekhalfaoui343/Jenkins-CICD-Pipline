package com.visualpathit.account.utils;

import org.springframework.stereotype.Service;

import com.visualpathit.account.beans.Components;

/**
 * Provides access to RabbitMQ connection settings.
 */
@Service
public final class RabbitMqUtil {
    /** Injected application configuration. */
    private final Components components;

    /**
     * Creates the utility with injected application configuration.
     *
     * @param configuration application configuration bean
     */
    public RabbitMqUtil(final Components configuration) {
        this.components = configuration;
    }

    /**
     * Returns the configured RabbitMQ host.
     *
     * @return RabbitMQ host
     */
    public String getRabbitMqHost() {
        return components.getRabbitMqHost();
    }

    /**
     * Returns the configured RabbitMQ port.
     *
     * @return RabbitMQ port
     */
    public String getRabbitMqPort() {
        return components.getRabbitMqPort();
    }

    /**
     * Returns the configured RabbitMQ username.
     *
     * @return RabbitMQ username
     */
    public String getRabbitMqUser() {
        return components.getRabbitMqUser();
    }

    /**
     * Returns the configured RabbitMQ password.
     *
     * @return RabbitMQ password
     */
    public String getRabbitMqPassword() {
        return components.getRabbitMqPassword();
    }
}
