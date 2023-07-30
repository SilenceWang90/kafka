package com.wp.kafka.quickstart.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @Description 生产者拦截器，ProducerInterceptor泛型为消息的key和value的类型
 * @Author admin
 * @Date 2023/7/30 16:48
 */
@Slf4j
public class CustomProducerInterceptor implements ProducerInterceptor<String, String> {
    /**
     * 发送消息之前的切面拦截
     *
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        log.info("生产者发送消息前置拦截器");
        String modifyValue = "prefix-" + record.value();
        return new ProducerRecord<>(record.topic()
                , record.partition()
                , record.timestamp()
                , record.key()
                , modifyValue
                , record.headers());
    }

    /**
     * 发送消息之后的切面拦截
     *
     * @param metadata
     * @param exception
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        log.info("生产者发送消息后置拦截器");
        if (exception == null) {
            //todo：消息发送失败处理，比如统计并记录日志等
        } else {
            //todo：消息发送成功处理，比如统计并记录日志等
        }
    }

    /**
     * 生产者关闭和broker的链接时调用
     */
    @Override
    public void close() {
        log.info("生产者关闭");
        //todo：根据需要~~~比如将上面的统计次数进行聚合运算，如统计成功率等~
    }

    @Override
    public void configure(Map<String, ?> configs) {
        //todo：
        configs.forEach((k, v) -> {
            System.out.println("key为：" + k);
            System.out.println("value为：" + v);
        });
    }
}
