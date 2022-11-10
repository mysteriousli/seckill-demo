package com.example.seckilldemo;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.seckilldemo.mapper.BlogMapper;
import com.example.seckilldemo.rabbitmq.MQSender;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SeckillDemoApplicationTests {
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Autowired
//    private RedisScript<Boolean> script;
//    @Test
//    void contextLoads() {
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        //占位，如果key不存在才可以设置成功
//        Boolean isLock = valueOperations.setIfAbsent("k1", "v1");
//        //如果占位成功，进行正常操作
//        if (isLock) {
//            valueOperations.set("name", "xxxx");
//            String name = (String) valueOperations.get("mame");
//            System.out.println("name = " + name);
//            //操作结束，删除锁
//            redisTemplate.delete("k1");
//        } else {
//            System.out.println("有线程在使用，请稍后再试！");
//        }
//    }
//
//    @Test
//    public void testLock02() {
//    AnnotationConfigApplicationContext
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        //添加一个过期时间，防止在应用过程中无法删除锁
//        Boolean isLock = valueOperations.setIfAbsent("k1", "v1", 5, TimeUnit.SECONDS);
//        if (isLock) {
//            valueOperations.set("name", "xxxx");
//            String name = (String) valueOperations.get("name");
//            System.out.println("name = " + name);
//            //抛异常
////            Integer.parseInt("xxxx");
//            //删除k1
//            redisTemplate.delete("k1");
//        } else {
//            System.out.println("有线程在使用，稍后再试！");
//        }
//    }
//
//    /**
//     * lua脚本，原子性，多个命令一次性执行
//     **/
//    @Test
//    public void testLock03() {
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        String value = UUID.randomUUID().toString();
//        Boolean isLock = valueOperations.setIfAbsent("k1", value, 120, TimeUnit.SECONDS);
//        if (isLock){
//            valueOperations.set("name","xxxx");
//            String name = (String) valueOperations.get("name");
//            System.out.println("name = " + name);
//            System.out.println(valueOperations.get("k1"));
//            Boolean result = (Boolean) redisTemplate.execute(script, Collections.singletonList("k1"),value);
//            System.out.println(result);
//        }else {
//            System.out.println("有线程在使用！");
//        }
//    }
//    public static void main(String[] args) {
//        DataSource dataSource = getBlogDataSource();
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(BlogMapper.class);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
//        System.out.println(blogMapper.selectBlog());
//    }
//    public static DataSource getBlogDataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/seckill?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
//        druidDataSource.setUsername("root");
//        druidDataSource.setPassword("123456");
//        return druidDataSource;
//    }

    @Autowired
    private MQSender mqSender;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 单个确认
    @Test
    public void publishMessageIndividually(){
//        mqSender.sendSingleMessage();
//        ConfirmCallback confirmCallback =
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 相关配置信息
             * @param b exchange交换机是否收到了消息，true成功，false失败
             * @param s 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("confirm方法执行了");
                if (b){
                    // 接收成功
                    System.out.println("接收成功消息"+s);
                }else {
                    // 接收失败
                    System.out.println("接收失败"+s);
                }
            }
        });
//        mqSender.sendSingleMessage("发送信息");
    }

    /**
     * 回退模式：当消息发送给exchange后，exchange路由到Queue失败是才会执行ReturnCallBack
     *步骤：
     * 1.开启回退模式
     * 2.设置ReturnCallBack
     * 3.设置Exchange处理消息的模式:
     *   1.如果消息没有路由到queue，则丢弃消息
     *   2.如果消息没有路由到queue，则返回给消息发送方ReturnCallBack
     */
    @Test
    public void testReturn(){
        // 设置交换机处理失败消息的模式
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            /**
             *
             * @param returnedMessage
             */
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("return 执行了...");
                // 消息对象
                System.out.println(returnedMessage.getMessage());
                // 错误码
                System.out.println(returnedMessage.getReplyCode());
                // 错误内容
                System.out.println(returnedMessage.getReplyText());
                // 交换机
                System.out.println(returnedMessage.getExchange());
                // 路由键
                System.out.println(returnedMessage.getRoutingKey());
            }
        });
//        mqSender.sendSingleMessage("发送信息");
    }

    /**
     * TTL：过期时间
     *步骤：
     * 1.队列统一过期
     * 2.消息单独过期
     * 如果设置了消息的过期时间，也设置了队列的过期时间，它以时间短的为准
     * 队列过期后，会将队列所有消息全部移除
     * 消息过期后：只有消息在队列顶端，才会判断其是否过期（移除掉）
     */
    @Test
    public void testTtl(){
        //消息后处理对象，设置一些消息的参数信息
        MessagePostProcessor postProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置message的信息
                message.getMessageProperties().setExpiration("5000"); //设置消息过期的时间
                // 返回信息
                return message;
            }
        };
        //消息单独过期
        mqSender.sendSingleMessage("发送信息", postProcessor);
    }
    @Test
    public void testDelay(){
        //1.发送订单消息，将来是在订单系统中，下单成功后，发送消息
//        rabbitTemplate.convertAndSend("");
    }
}
