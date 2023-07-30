package com.wp.kafka.quickstart;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @Description
 * @Author admin
 * @Date 2023/7/26 13:38
 */
@Slf4j
public class QuickStartProducer {
    public static void main(String[] args) {
        /** 1、配置生产者启动的关键参数 **/
        Properties properties = new Properties();
        // （1）链接kafka集群的服务列表，如果有多个则逗号分隔
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // （2）标记kafka client的ID。用于区分多个连接到kafka的client唯一标识
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "topic-normal-producer");
        // （3）发送消息的序列化处理。KEY和VALUE都要做序列化处理，key用于消息分区(partition)，value是消息本身
        // Kafka Broker只能处理二进制数据，因此必须做序列化处理
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        /** 2、创建kafka生产者对象，传递properties属性参数集合 **/
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        /** 3、构造消息内容 **/
        User user = new User("001", "王泽霖");
        // ProducerRecord是Kafka消息体，将要发送的消息放入ProducerRecord
        // 构造函数要放入topic以及消息本身
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-normal", user.toString());

        /** 4、发送消息 **/
        producer.send(producerRecord,(RecordMetadata metadata, Exception exception)->{
            if(exception!=null){
                exception.printStackTrace();
                return;
            }
            log.info("分区为:{},时间戳为:{},偏移量为:{}",metadata.partition(),metadata.timestamp(),metadata.offset());
        });
    }
}
