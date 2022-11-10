package com.example.seckilldemo.rabbitmq;

import com.example.seckilldemo.pojo.SeckillMessage;
import com.example.seckilldemo.pojo.SeckillOrder;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.service.IGoodsService;
import com.example.seckilldemo.service.IOrderService;
import com.example.seckilldemo.utils.JsonUtil;
import com.example.seckilldemo.vo.GoodsVo;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {
    //    @RabbitListener(queues = "queue")
//    public void receive(Object msg) {
//        log.info("queue接收消息：" + msg);
//    }
//    @RabbitListener(queues = "queue_fanout01")
//    public void receive01(Object msg) {
//        log.info("queue_fanout01接收消息：" + msg);
//    }
//    @RabbitListener(queues = "queue_fanout02")
//    public void receive02(Object msg) {
//        log.info("queue_fanout02接收消息：" + msg);
//    }
//    @RabbitListener(queues = "queue_direct01")
//    public void receive03(Object msg) {
//        log.info("queue_direct01接收消息：" + msg);
//    }
//
//    @RabbitListener(queues = "queue_direct02")
//    public void receive04(Object msg) {
//        log.info("queue_direct02接收消息：" + msg);
//    }
//
//    @RabbitListener(queues = "queue_topic01")
//    public void receive05(Object msg) {
//        log.info("queue_topic01接收消息：" + msg);
//    }
//
//    @RabbitListener(queues = "queue_topic02")
//    public void receive06(Object msg) {
//        log.info("queue_topic02接收消息：" + msg);
//    }
//
//    @RabbitListener(queues = "queue_header01")
//    public void receive07(Message msg) {
//        log.info("queue_header01接收消息：" + new String(msg.getBody()));
//    }
//
//    @RabbitListener(queues = "queue_header02")
//    public void receive08(Message msg) {
//        log.info("queue_header02接收消息：" + new String(msg.getBody()));
//    }
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;

    /**
     * 在队列中接受消息，执行下单操作
     *
     * @param msg
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String msg) {
        log.info("queue接收消息：" + msg);
        // 解析消息，获取秒杀信息user和goodsId
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodId();
        User user = seckillMessage.getUser();
        // 通过goodsId，获取goodsVo
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        // 判断秒杀商品数量
        if (goodsVo.getStockCount() < 1) {
            return;
        }
        // 判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return;
        }
        //下单操作
        orderService.seckill(user, goodsVo);
    }
    @RabbitListener(queues = "queue_topic02")
    public void receive02(String msg){
        log.info("queue_test"+msg);
    }
}
