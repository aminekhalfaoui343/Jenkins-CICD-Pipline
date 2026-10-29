package com.visualpathit.account.service;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.visualpathit.account.utils.RabbitMqUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Service
public final class ProducerServiceImpl implements ProducerService {
    /** Logger for RabbitMQ publishing errors. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProducerServiceImpl.class);

    /** Name of the message exchange. */
    private static final String EXCHANGE_NAME = "messages";

    /** RabbitMQ connection settings. */
    private final RabbitMqUtil rabbitMqUtil;

    /**
     * Creates the producer with RabbitMQ connection settings.
     *
     * @param settings RabbitMQ connection settings
     */
    public ProducerServiceImpl(final RabbitMqUtil settings) {
        this.rabbitMqUtil = settings;
    }

    /**
     * Publishes a message to the configured fanout exchange.
     *
     * @param message message body
     * @return the existing response value
     */
    @Override
    public String produceMessage(final String message) {
        Objects.requireNonNull(message, "message");

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(rabbitMqUtil.getRabbitMqHost());
            factory.setPort(Integer.parseInt(rabbitMqUtil.getRabbitMqPort()));
            factory.setUsername(rabbitMqUtil.getRabbitMqUser());
            factory.setPassword(rabbitMqUtil.getRabbitMqPassword());
            try (Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
                channel.basicPublish(
                        EXCHANGE_NAME, "", null,
                        message.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException io) {
            LOGGER.error("Unable to publish RabbitMQ message", io);
        } catch (TimeoutException toe) {
            LOGGER.error("Timed out while publishing RabbitMQ message", toe);
        }
        return "response";
    }
}
