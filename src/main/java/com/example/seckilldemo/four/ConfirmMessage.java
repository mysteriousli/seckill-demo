package com.example.seckilldemo.four;

import com.example.seckilldemo.rabbitmq.MQSender;
import com.rabbitmq.client.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 发布确认模式
 * 1、单个确认
 * 2、批量确认
 * 3、异步批量确认
 */
public class ConfirmMessage {
    @Autowired
    private MQSender mqSender;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public static void main(String[] args) {

    }
    // 单个确认
    public static void publishMessageIndividually(){
//        mqSender.sendSingleMessage();
//        ConfirmCallback confirmCallback =
    }
}
