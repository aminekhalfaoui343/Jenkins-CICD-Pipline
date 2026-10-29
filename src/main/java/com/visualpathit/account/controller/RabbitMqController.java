package com.visualpathit.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.visualpathit.account.utils.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Controller
public final class RabbitMqController {
    /** RabbitMQ connection settings. */
    private final RabbitMqUtil rabbitMqUtil;

    /**
     * Creates the controller with RabbitMQ connection settings.
     *
     * @param settings RabbitMQ connection settings
     */
    public RabbitMqController(final RabbitMqUtil settings) {
        this.rabbitMqUtil = settings;
    }

    /**
     * Checks whether a RabbitMQ connection can be opened.
     *
     * @return model and view for the RabbitMQ connection status
     */
    @GetMapping("/user/rabbit")
    public ModelAndView checkRabbitMqStatus() {
        ModelAndView modelAndView = new ModelAndView();
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setHost(rabbitMqUtil.getRabbitMqHost());
            factory.setPort(Integer.parseInt(rabbitMqUtil.getRabbitMqPort()));
            factory.setUsername(rabbitMqUtil.getRabbitMqUser());
            factory.setPassword(rabbitMqUtil.getRabbitMqPassword());
            try (Connection connection = factory.newConnection()) {
                if (connection.isOpen()) {
                    modelAndView.setViewName("rabbitmq");
                } else {
                    modelAndView.setViewName("rabbitmq-error");
                }
            }
        } catch (IOException | TimeoutException
                | NumberFormatException exception) {
            modelAndView.setViewName("rabbitmq-error");
        }
        return modelAndView;
    }
}
