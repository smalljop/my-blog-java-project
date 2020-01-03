package com.smalljop.redis.example.queue;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * @description: 消息提供者
 * @author: smalljop
 * @create: 2020-01-03 10:44
 **/
@Component
@Slf4j
@AllArgsConstructor
public class MessageProvider {

    private final DelayingQueueService delayingQueueService;

    private static String USER_CHANNEL = "USER_CHANNEL";

    /**
     * 发送消息
     *
     * @param messageContent
     */
    public void sendMessage(String messageContent, long delay) {
        try {
            if (messageContent != null) {
                String seqId = UUID.randomUUID().toString();
                Message message = new Message();
                //时间戳默认为毫秒 延迟5s即为 5*1000
                long time = System.currentTimeMillis();
                LocalDateTime dateTime = Instant.ofEpochMilli(time).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
                message.setDelayTime(time + (delay * 1000));
                message.setCreateTime(dateTime);
                message.setBody(messageContent);
                message.setId(seqId);
                message.setChannel(USER_CHANNEL);
                delayingQueueService.push(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
