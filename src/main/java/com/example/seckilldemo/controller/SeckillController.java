//package com.example.seckilldemo.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.example.seckilldemo.pojo.Order;
//import com.example.seckilldemo.pojo.SeckillMessage;
//import com.example.seckilldemo.pojo.SeckillOrder;
//import com.example.seckilldemo.pojo.User;
//import com.example.seckilldemo.rabbitmq.MQSender;
//import com.example.seckilldemo.service.IGoodsService;
//import com.example.seckilldemo.service.IOrderService;
//import com.example.seckilldemo.service.ISeckillOrderService;
//import com.example.seckilldemo.utils.JsonUtil;
//import com.example.seckilldemo.vo.GoodsVo;
//import com.example.seckilldemo.vo.RespBean;
//import com.example.seckilldemo.vo.RespBeanEnum;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.data.redis.core.script.RedisScript;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/seckill")
//public class SeckillController implements InitializingBean {
//    @Autowired
//    private IGoodsService goodsService;
//    @Autowired
//    private ISeckillOrderService seckillOrderService;
//    @Autowired
//    private IOrderService orderService;
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    private MQSender mqSender;
//    @Autowired
//    private RedisScript<Long> script;
//
//    private Map<Long, Boolean> emptyStockMap = new HashMap<>();
//
//    @RequestMapping(value = "/doSeckill2", method = RequestMethod.POST)
//    public String doSeckill2(Model model, User user, Long goodsId) {
//        if (user == null) {
//            return "login";
//        }
//        model.addAttribute("user", user);
//        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//        if (goodsVo.getStockCount() < 1) {
//            model.addAttribute("errmsg", RespBeanEnum.EMPTY_ERROR.getMessage());
//            return "seckillFail";
//        }
//        //????????????????????????
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id",
//                user.getId()).eq("goods_id", goodsId));
//        if (seckillOrder != null) {
//            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
//            return "seckillFail";
//        }
//        Order order = orderService.seckill(user, goodsVo);
//        model.addAttribute("order", order);
//        model.addAttribute("goods", goodsVo);
//        return "orderDetail";
//    }
//
//    /**
//     * ????????????
//     * @param model
//     * @param user
//     * @param goodsId
//     * @return
//     */
//    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
//    @ResponseBody
//    public RespBean doSeckill(Model model, User user, Long goodsId) {
//        // ????????????????????????
//        if (user == null) {
//            return RespBean.error(RespBeanEnum.SESSION_ERROR);
//        }
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        //????????????????????????
//        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
//        if (seckillOrder != null) {
//            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
//        }
//        //?????????????????????????????????
//        if (emptyStockMap.get(goodsId)) {
//            return RespBean.error(RespBeanEnum.EMPTY_ERROR);
//        }
//        //????????????
////        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
//        // ???lua?????????????????????????????????
//        Long stock = redisTemplate.execute(script, Collections.singletonList("seckillGoods:" + goodsId), Collections.EMPTY_LIST);
//        // ???redis???????????????0????????????map??????
//        if (stock < 0) {
//            emptyStockMap.put(goodsId, true);
//            valueOperations.increment("seckillGoods:" + goodsId);
//            return RespBean.error(RespBeanEnum.EMPTY_ERROR);
//        }
//        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
//        // ????????????????????????????????????
//        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
//        return RespBean.success(0);
////        SeckillOrder
////        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
////
////        if (goodsVo.getStockCount() < 1) {
////            model.addAttribute("errmsg", RespBeanEnum.EMPTY_ERROR.getMessage());
////            return RespBean.error(RespBeanEnum.EMPTY_ERROR);
////        }
////        //????????????????????????
//////        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id",
//////                user.getId()).eq("goods_id", goodsId));
////        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsVo.getId());
////        if (seckillOrder != null){
////            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
////            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
////        }
////        Order order = orderService.seckill(user, goodsVo);
////        return RespBean.success(order);
////        return null;
//    }
//
//    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
//    @ResponseBody
//    public RespBean getResult(User user, Long goodsId) {
//        if (user == null) {
//            return RespBean.error(RespBeanEnum.SESSION_ERROR);
//        }
//        // ??????????????????
//        Long orderId = seckillOrderService.getResult(user, goodsId);
//        return RespBean.success(orderId);
//    }
//
//    /**
//     * ?????????????????????????????????????????????redis???
//     *
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // ????????????
//        List<GoodsVo> list = goodsService.findGoodsVo();
//        // ??????????????????????????????
//        if (CollectionUtils.isEmpty(list)) {
//            return;
//        }
//        // ????????????????????????redis???
//        list.forEach(goodsVo -> {
//                    // ?????????id??????????????????????????????redis???
//                    redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
//                    // ??????????????????????????????????????????map???
//                    emptyStockMap.put(goodsVo.getId(), false);
//                }
//        );
//    }
//}
