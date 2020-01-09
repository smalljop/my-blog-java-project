package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.BloomOperations;
import org.springframework.data.redis.core.DefaultBloomOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * @author smalljop
 */
@ConditionalOnBean(StringRedisTemplate.class)
public class BloomAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(BloomOperations.class)
    public BloomOperations bloomOperations(StringRedisTemplate redisTemplate) {
        return new DefaultBloomOperations(redisTemplate);
    }
}
