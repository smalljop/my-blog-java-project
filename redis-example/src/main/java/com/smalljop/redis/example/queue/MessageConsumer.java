package com.smalljop.redis.example.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @description: 延迟队列消费
 * @author: smalljop
 * @create: 2020-01-03 10:51
 **/
@Component
@Slf4j
@AllArgsConstructor
public class MessageConsumer {
    private static ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
    private final DelayingQueueService delayingQueueService;

    /**
     * 定时消费队列中的数据
     * zset会对score进行排序 让最早消费的数据位于最前
     * 拿最前的数据跟当前时间比较 时间到了则消费
     */
    @Scheduled(cron = "*/1 * * * * *")
    public void consumer() throws JsonProcessingException {
        List<Message> msgList = delayingQueueService.pull();
        if (null != msgList) {
            long current = System.currentTimeMillis();
            msgList.stream().forEach(msg -> {
                // 已超时的消息拿出来消费
                if (current >= msg.getDelayTime()) {
                    try {
                        log.info("消费消息：{}:消息创建时间：{},消费时间：{}", mapper.writeValueAsString(msg), msg.getCreateTime(), LocalDateTime.now());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    //移除消息
                    delayingQueueService.remove(msg);
                }
            });
        }
    }


}
