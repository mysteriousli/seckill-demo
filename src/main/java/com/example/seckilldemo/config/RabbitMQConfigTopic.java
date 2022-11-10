package com.example.seckilldemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfigTopic {
//    private static final String QUEUE01 = "queue_topic01";
//    private static final String QUEUE02 = "queue_topic02";
//    private static final String EXCHANGE = "topicExchange";
//    private static final String QUEUE0KEY01 = "#.queue.#";
//    private static final String QUEUE0KEY02 = "*.queue.#";//*至少要有一个


    private static final String QUEUE = "seckillQueue";
    private static final String QUEUE02 = "queue_topic02";
    private static final String EXCHANGE = "seckillExchange";

    private static final String BUSINESSEXCHANGE = "businessExchange";
    private static final String BUSINESSQUEUE = "businessQueue";
    private static final String DEADEXCHANGE = "deadExchange";
    private static final String DEADQUEUE = "deadQueue";
    @Bean
    // 设置缓存队列
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    // 设置Topic交换机
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    // 绑定交换机、队列与匹配方式 #代表多个单词
    public Binding binding01() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("seckill.#");
    }
    @Bean
    // 设置缓存队列
    public Queue queue02() {
        return new Queue(QUEUE02);
    }

    @Bean
    // 设置Topic交换机
    public TopicExchange topicExchange02() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    // 绑定交换机、队列与匹配方式 #代表多个单词
    public Binding binding02() {
        return BindingBuilder.bind(queue02()).to(topicExchange()).with("test.#");
    }

    @Bean
    public Queue queueBusiness(){
        Map<String, Object> args = new HashMap<>();
        //绑定死信交换机
        args.put("x-dead-letter-exchange", DEADEXCHANGE);
        //绑定死信路由key
        args.put("x-dead-letter-routing-key", "dlx.cancel");
        //设置队列的过期时间
        args.put("x-message-ttl", 30000);
        return QueueBuilder.durable().withArguments(args).build();
    }

    @Bean
    // 设置死信Topic交换机
    public TopicExchange topicExchangeBusiness() {
        return new TopicExchange(BUSINESSEXCHANGE);
    }

    @Bean
    // 绑定交换机、队列与匹配方式 #代表多个单词
    public Binding bindingBusiness() {
        return BindingBuilder.bind(queueBusiness()).to(topicExchangeBusiness()).with("dlx.#");
    }

    @Bean
    // 设置缓存队列
    public Queue queueDlx() {
        return new Queue(DEADQUEUE);
    }

    @Bean
    // 设置Topic交换机
    public TopicExchange topicExchangeDlx() {
        return new TopicExchange(DEADEXCHANGE);
    }

    @Bean
    // 绑定交换机、队列与匹配方式 #代表多个单词
    public Binding bindingDlx() {
        return BindingBuilder.bind(queueDlx()).to(topicExchangeDlx()).with("test.#");
    }

}
