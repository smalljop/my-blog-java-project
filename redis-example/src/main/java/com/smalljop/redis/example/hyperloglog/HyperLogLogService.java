package com.smalljop.redis.example.hyperloglog;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: smalljop
 * @create: 2020-01-06 11:38
 **/
@Component
@Slf4j
@AllArgsConstructor
public class HyperLogLogService {

    private final StringRedisTemplate redisTemplate;


    /**
     * 添加值
     * pfadd 指令
     *
     * @param key
     * @param values
     */
    public void add(String key, String... values) {
        redisTemplate.opsForHyperLogLog().add(key, values);
    }


    /**
     * 统计数量
     * pfcount 指令
     *
     * @param key
     */
    public Long count(String key) {
        return redisTemplate.opsForHyperLogLog().size(key);
    }


    /**
     * 合并 用于将多个 pf 计数值累加在一起形成一个新的 pf 值
     * pfmerge
     *
     * @param keys
     */
    public void merge(String key1, String... keys) {
        redisTemplate.opsForHyperLogLog().union(key1, keys);
    }
}
