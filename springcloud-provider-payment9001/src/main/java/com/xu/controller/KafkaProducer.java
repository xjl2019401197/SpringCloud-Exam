package com.xu.controller;

import com.xu.config.BeanUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
@Component
public class KafkaProducer {
//    @Resource

    KafkaTemplate<String, Object> kafkaTemplate = BeanUtils.getBean(KafkaTemplate.class);
    // 发送消息
    static String[] emails= {"xu2550908862@163.com","2550908862@qq.com"};
    public   void sendMessage1() {
        System.out.println("开始发布");
        for (String email : emails) {
            kafkaTemplate.send("listener",email);
        }
    }
    // 消费监听
    @KafkaListener(topics = {"listener"})
    public void onMessage1(ConsumerRecord<?, ?> record) throws IOException, GeneralSecurityException, MessagingException {
        System.out.println(record.value().toString());
        SendEamil.abc(record.value().toString());
//        System.out.println("简单消费1："+record.topic()+"-"+record.partition()+"-"+record.value());
    }


}
