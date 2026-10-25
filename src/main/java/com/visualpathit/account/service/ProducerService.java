package com.visualpathit.account.service;

/**
 * Publishes messages to the application's message broker.
 */
public interface ProducerService {
    /**
     * Publishes a message.
     *
     * @param message message body to publish
     * @return existing publish response
     */
    String produceMessage(String message);
}
