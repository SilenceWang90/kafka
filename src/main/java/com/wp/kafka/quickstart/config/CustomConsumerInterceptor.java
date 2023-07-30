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
        //todo：可记录消息处理时间等，需要返回ConsumerRecords<String, String>，可对records中的内容进行处理然后再返回
        return records;
    }

    /**
     * 消费者处理完成后的拦截器
     *
     * @param offsets 分区以及对应分区中消息的offset和Metadata信息
     */
    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        /** 每次提交的时候都会被调用一次，无论是自动还是手动。
         * 手动提交：KafkaConsumer.commitSync()/KafkaConsumer.commitAsync()等方法即可。注意：手动提交是将此次poll()的全部数据中offset最大的偏移量进行提交！！！
         * 自动提交：如果设置每隔多久自动提交一次的话此方法会被不断重复执行~相关参数如下：**/
        // 消费者提交offset：自动提交以及手动提交，true是自动提交，false是手动提交
        // properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 提交时机：每隔5秒提交一次
        // properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
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
