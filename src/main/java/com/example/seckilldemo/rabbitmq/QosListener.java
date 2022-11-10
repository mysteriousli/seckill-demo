package com.example.seckilldemo.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * Consumer 限流：
 * 1.确保ack机制为手动确认
 * 1.listener-container配置属性
 *     perfetch=1，表示消费端每次从mq拉取一条消息消费，消费完才能拉取下一条消息
 */
public class QosListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(new String(message.getBody()));
//        channel.
    }
}
