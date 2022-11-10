//package com.example.seckilldemo.controller;
//
//
//import com.example.seckilldemo.pojo.User;
//import com.example.seckilldemo.rabbitmq.MQSender;
//import com.example.seckilldemo.vo.RespBean;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author liguangyuan
// * @since 2022-04-04
// */
//@Controller
//@RequestMapping("/user")
//@Slf4j
//public class UserController {
//    @Autowired
//    private MQSender mqSender;
//    /**
//     * 用户测试
//     * @param user
//     * @return
//     */
//    @RequestMapping("/info")
//    @ResponseBody
//    public RespBean info(User user){
//        log.info(user.toString());
//        return RespBean.success(user);
//    }
//
////    /**
////     * 发送消息
////     */
////    @RequestMapping("/mq")
////    @ResponseBody
////    public void mq(){
////        mqSender.send("hello");
////    }
////
////    /**
////     * 发送消息交换机模式
////     */
////    @RequestMapping("/mq/exchange")
////    @ResponseBody
////    public void mq01(){
////        mqSender.send("hello");
////    }
////
////    /**
////     * 发送消息指定模式
////     */
////    @RequestMapping("/mq/direct01")
////    @ResponseBody
////    public void mq02(){
////        mqSender.send01("hello,red");
////    }
////
////    /**
////     * 发送消息指定模式
////     */
////    @RequestMapping("/mq/direct02")
////    @ResponseBody
////    public void mq03(){
////        mqSender.send02("hello,green");
////    }
////    /**
////     * 发送消息指定模式
////     */
////    @RequestMapping("/mq/topic01")
////    @ResponseBody
////    public void mq04(){
////        mqSender.send03("hello,red");
////    }
////
////    /**
////     * 发送消息指定模式
////     */
////    @RequestMapping("/mq/topic02")
////    @ResponseBody
////    public void mq05(){
////        mqSender.send04("hello,green");
////    }
////    /**
////     * 发送消息指定模式
////     */
////    @RequestMapping("/mq/header01")
////    @ResponseBody
////    public void mq06(){
////        mqSender.send05("hello,red");
////    }
////
////    /**
////     * 发送消息指定模式
////     */
////    @RequestMapping("/mq/header02")
////    @ResponseBody
////    public void mq07(){
////        mqSender.send06("hello,green");
////    }
//}
