package org.springframework.data.redis.core;

import java.util.List;
import java.util.Objects;

public class DefaultBloomOperations<K, V> extends AbstractOperations<K, V> implements BloomOperations<K, V> {

    public DefaultBloomOperations(RedisTemplate<K, V> template) {
        super(template);
    }

    @Override
    public void createFilter(K key, double errorRate, long initCapacity) {
        byte[] rawKey = rawKey(key);
        byte[] rawErrorRate = rawString(String.valueOf(errorRate));
        byte[] rawInitCapacity = rawString(String.valueOf(initCapacity));
        this.execute(connection -> {
            connection.execute(BloomCommand.RESERVE.getCommand(), rawKey, rawErrorRate, rawInitCapacity);
            return null;
        }, true);
    }

    @Override
    public Boolean add(K key, V value) {
        byte[] rawKey = rawKey(key);
        byte[] rawValue = rawValue(value);
        return this.execute(connection -> {
            Long l = (Long) connection.execute(BloomCommand.ADD.getCommand(), rawKey, rawValue);
            return Objects.equals(l, 1L);
        }, true);
    }


    @Override
    public Boolean[] addMulti(K key, V... values) {
        byte[][] rawArgs = rawArgs(key, values);
        return this.execute(connection -> {
            List<Long> ls = (List<Long>) connection.execute(BloomCommand.MADD.getCommand(), rawArgs);
            return ls.stream().map(l -> Objects.equals(l, 1L)).toArray(Boolean[]::new);
        }, true);
    }

    @Override
    public Boolean exists(K key, V value) {
        byte[] rawKey = rawKey(key);
        byte[] rawValue = rawValue(value);
        return this.execute(connection -> {
            Long l = (Long) connection.execute(BloomCommand.EXISTS.getCommand(), rawKey, rawValue);
            return Objects.equals(l, 1L);
        }, true);
    }

    @Override
    public Boolean[] existsMulti(K key, V... values) {
        byte[][] rawArgs = rawArgs(key, values);
        return this.execute(connection -> {
            List<Long> ls = (List<Long>) connection.execute(BloomCommand.MEXISTS.getCommand(), rawArgs);
            return ls.stream().map(l -> Objects.equals(l, 1L)).toArray(Boolean[]::new);
        }, true);
    }

    @Override
    public Boolean delete(K key) {
        return template.delete(key);
    }

    private byte[][] rawArgs(Object key, Object... values) {
        byte[][] rawArgs = new byte[1 + values.length][];

        int i = 0;
        rawArgs[i++] = rawKey(key);

        for (Object value : values) {
            rawArgs[i++] = rawValue(value);
        }

        return rawArgs;
    }
}