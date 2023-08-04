package com.wp.kafka.controller;

import com.wp.kafka.formality.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author admin
 * @Date 2023/8/4 16:30
 */
@RestController
@RequestMapping("/testKafka")
public class TestController {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @RequestMapping("test")
    public String test() {
        for (int i = 0; i < 10; i++) {
            kafkaProducerService.sendMessage("topic02", "hello kafka" + i + ",我叫王泽霖");
        }
        return "success";
    }
}
