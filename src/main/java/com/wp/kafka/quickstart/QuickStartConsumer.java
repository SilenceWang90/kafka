package com.wp.kafka.quickstart;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @Description
 * @Author admin
 * @Date 2023/7/26 13:38
 */
@Slf4j
public class QuickStartConsumer {
    public static void main(String[] args) {
        /** 1、配置消费者启动的关键参数 **/
        Properties properties = new Properties();
        // （1）配置Kafka Broker
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9091");
        // （2）接收消息的反序列化处理。KEY和VALUE都要做序列化处理，key用于消息分区(partition)，value是消息本身
        // Kafka Broker只能处理二进制数据，因此必须做序列化和反序列化处理
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // （3）重要属性：消费者订阅组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "quickstart-group");
        // （4）连接超时配置，单位是毫秒
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        // （5）消费者提交offset：自动提交以及手动提交，true是自动提交，false是手动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // （6）提交时机：每隔5秒提交一次
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);

        /** 2、创建消费者对象 **/
        KafkaConsumer<String, User> consumer = new KafkaConsumer<>(properties);

        /** 3、订阅主题，接收消息 **/
        consumer.subscribe(Collections.singletonList("test-quickstart"));
        log.info("quickstart consumer started");

        /** 4、采用主动拉取消息的方式消费数据 **/
        while (true) {
            // 等待多久拉取一次主题中的消息
            ConsumerRecords<String, User> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            // 消息存储在partition中，一个topic可以有多个partition，因此遍历所有的partion将topic中对应的所有消息全部获取到
            consumerRecords.partitions().forEach(partition -> {
                // 从partition中获取kafka消息
                List<ConsumerRecord<String, User>> partitionRecords = consumerRecords.records(partition);
                log.info("获取消息的topic：{}，分区号：{}，消息总数：{}", partition.topic(), partition.partition(), partitionRecords.size());
                partitionRecords.forEach(message -> {
                    // kafka设置的消息的key
                    String key = message.key();
                    // 消息本身
                    User value = message.value();
                    // 获取的消息在当前partition的偏移量
                    long offset = message.offset();
                    // 手动提交偏移量的话，offset加一处理
                    long commitOffset = offset + 1;
                    log.info("获取实际消息：{}，消息的offset：{}，提交offset：{}", value, offset, commitOffset);
                });
            });
        }
    }
}
