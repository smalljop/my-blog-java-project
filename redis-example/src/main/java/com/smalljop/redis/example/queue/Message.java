package com.smalljop.redis.example.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 消息统一封装类
 * @author: qing.wang.o
 * @create: 2020-01-03 10:20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    /**
     * 消息唯一标识
     */
    private String id;
    /**
     * 消息渠道 如 订单 支付 代表不同业务类型
     * 为消费时不同类去处理
     */
    private String channel;
    /**
     * 具体消息 json
     */
    private String body;

    /**
     * 延时时间 被消费时间  取当前时间戳+延迟时间
     */
    private Long delayTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
