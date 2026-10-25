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
public class ProducerServiceImpl implements ProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServiceImpl.class);

    /**
     *  The name of the Exchange
     */
    private static final String EXCHANGE_NAME = "messages";

    /**
     *  This method publishes a message
     * @param message
     */
    @Override
    public String produceMessage(String message) {
        Objects.requireNonNull(message, "message");

        try {
            ConnectionFactory factory = new ConnectionFactory();
            /**
            * System.out.println("Rabitmq host: ::" + RabbitMqUtil.getRabbitMqHost());
            * System.out.println("Rabitmq port: ::" + RabbitMqUtil.getRabbitMqPort());
            * System.out.println("Rabitmq user: ::" + RabbitMqUtil.getRabbitMqUser());
            * System.out.println("Rabitmq password: ::" + RabbitMqUtil.getRabbitMqPassword());
            **/
            factory.setHost(RabbitMqUtil.getRabbitMqHost());
            factory.setPort(Integer.parseInt(RabbitMqUtil.getRabbitMqPort()));
            factory.setUsername(RabbitMqUtil.getRabbitMqUser());
            factory.setPassword(RabbitMqUtil.getRabbitMqPassword());
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException io) {
            LOGGER.error("Unable to publish RabbitMQ message", io);
        } catch (TimeoutException toe) {
            LOGGER.error("Timed out while publishing RabbitMQ message", toe);
        }
        return "response";
    }
}
