package org.springframework.data.redis.core;

public interface BloomOperations<K, V> {

    /**
     * 创建一个布隆过滤器
     *
     * @param: key
     * @param: errorRate 错误比例
     * @param: initCapacity 初始大小
     */
    void createFilter(K key, double errorRate, long initCapacity);

    /**
     * 添加元素
     *
     * @param: key
     * @param: value
     * @return:
     */
    Boolean add(K key, V value);

    /**
     * 批量添加
     *
     * @param: key
     * @param: values
     * @return:
     */
    Boolean[] addMulti(K key, V... values);

    /**
     * 判断是否存在
     *
     * @param: key
     * @param: value
     * @return:
     */
    Boolean exists(K key, V value);

    /**
     * 批量检查元素是否存在
     *
     * @param: key
     * @param: values
     */
    Boolean[] existsMulti(K key, V... values);

    /**
     * 删除布隆过滤器
     *
     * @param: key
     * @return:
     */
    Boolean delete(K key);
}