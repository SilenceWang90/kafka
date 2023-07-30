package com.wp.kafka.quickstart.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

/**
 * @Description Kafka发送消息回调信息类
 * @Author admin
 * @Date 2023/7/30 14:38
 */
@Component
@Slf4j
public class KafkaSendCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if(exception!=null){
            exception.printStackTrace();
            return;
        }
        log.info("分区为:{},时间戳为:{},偏移量为:{}",metadata.partition(),metadata.timestamp(),metadata.offset());
    }
}
