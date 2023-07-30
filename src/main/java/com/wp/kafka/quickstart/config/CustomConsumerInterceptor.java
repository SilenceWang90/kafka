package com.wp.kafka.quickstart.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

/**
 * @Description 消费者拦截器，ConsumerInterceptor泛型为消息的key和value的类型
 * @Author admin
 * @Date 2023/7/30 16:49
 */
@Slf4j
public class CustomConsumerInterceptor implements ConsumerInterceptor<String, String> {
    /**
     * 消费者拿到消息，处理之前的拦截器
     *
     * @param records 获取到的消息体
     * @return
     */
    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        log.info("消费者前置处理器");
        //todo：可记录消息处理时间等，也可以对接收到的消息做改造然后return new ConsumerRecords()
        return null;
    }

    /**
     * 消费者处理完成后的拦截器
     *
     * @param offsets 分区以及对应分区中消息的offset和Metadata信息
     */
    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        log.info("消费者后置处理器");
    }

    /**
     * 消费者关闭时触发
     */
    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
