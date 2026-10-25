package com.visualpathit.account.service;

/**
 * Receives messages from the application's message broker.
 */
public interface ConsumerService {
    /**
     * Handles a received message.
     *
     * @param data message body
     */
    void consumerMessage(byte[] data);
}
