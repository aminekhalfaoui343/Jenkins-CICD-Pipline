package com.visualpathit.account.service;

import org.junit.Test;

public class ConsumerServiceImplTest {
    @Test
    public void consumesMessage() {
        new ConsumerServiceImpl().consumerMessage("payload".getBytes());
    }
}
