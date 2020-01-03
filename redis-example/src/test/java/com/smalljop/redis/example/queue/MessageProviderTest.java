package com.smalljop.redis.example.queue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageProviderTest {

    @Autowired
    private MessageProvider messageProvider;

    @Test
    void sendMessage() {
        messageProvider.sendMessage("同时发送消息1", 20);
        messageProvider.sendMessage("同时发送消息2", 20);
    }
}