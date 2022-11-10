package com.example.seckilldemo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //    public void send(Object msg) {
//        log.info("发送消息：" + msg);
//        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
//    }
//
//    public void send01(Object msg) {
//        log.info("发送red消息：" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue_red", msg);
//    }
//
//    public void send02(Object msg) {
//        log.info("发送green消息：" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue_green", msg);
//    }
//
//    public void send03(Object msg) {
//        log.info("发送red消息：" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "test.test.queue.test", msg);
//    }
//
//    public void send04(Object msg) {
//        log.info("发送red消息：" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "test.queue.test", msg);
//    }
//    public void send05(String msg) {
//        log.info("发送两个队列消息：" + msg);
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("color","red");
//        messageProperties.setHeader("speed","fast");
//        Message message = new Message(msg.getBytes(), messageProperties);
//        rabbitTemplate.convertAndSend("headerExchange", "", message);
//    }
//    public void send06(String msg) {
//        log.info("发送一个队列消息：" + msg);
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("color","red");
//        messageProperties.setHeader("speed","low");
//        Message message = new Message(msg.getBytes(), messageProperties);
//        rabbitTemplate.convertAndSend("headerExchange", "", message);
//    }

    /**
     * 发送秒杀信息
     * @param message
     */
    public void sendSeckillMessage(String message) {
        log.info("发送消息：" + message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", message);
    }

    /**
     * 发送单个确认信息
     * @param message
     */
    public void sendSingleMessage(String message, MessagePostProcessor messagePostProcessor) {
        log.info("发送消息：" + message);
        rabbitTemplate.convertAndSend("seckillExchange", "test.message", message,messagePostProcessor);
    }
}
