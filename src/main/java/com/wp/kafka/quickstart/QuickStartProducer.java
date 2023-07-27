package com.wp.kafka.quickstart;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * @Description
 * @Author admin
 * @Date 2023/7/26 13:38
 */
public class QuickStartProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // 1、配置生产者启动的关键参数

        // 2、创建kafka生产者对象，传递properties属性参数集合

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // 3、构造消息内容

        // 4、发送消息


    }
}
