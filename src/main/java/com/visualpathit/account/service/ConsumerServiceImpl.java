package com.visualpathit.account.service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public final class ConsumerServiceImpl implements ConsumerService {
    /** Name of the message exchange. */
    private static final String EXCHANGE_NAME = "messages";

    /**
     * Receives and displays a message from the RabbitMQ fanout exchange.
     *
     * @param data message body
     */
    @Override
    @RabbitListener(bindings = @QueueBinding(value = @Queue(),
            exchange = @Exchange(
                    value = EXCHANGE_NAME, type = ExchangeTypes.FANOUT)))
    public void consumerMessage(final byte[] data) {
        String consumedMessage = new String(data);
        System.out.println(" [x] Consumed  '" + consumedMessage + "'");
    }
}
