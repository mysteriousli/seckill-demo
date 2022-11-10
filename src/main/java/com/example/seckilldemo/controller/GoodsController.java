//package com.example.seckilldemo.controller;
//
//import com.example.seckilldemo.pojo.Goods;
//import com.example.seckilldemo.pojo.User;
//import com.example.seckilldemo.service.IGoodsService;
//import com.example.seckilldemo.service.IUserService;
//import com.example.seckilldemo.vo.DetailVo;
//import com.example.seckilldemo.vo.GoodsVo;
//import com.example.seckilldemo.vo.RespBean;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jsqlparser.statement.select.KSQLJoinWindow;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.Banner;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Controller
//@RequestMapping("/goods")
//@Slf4j
//public class GoodsController {
//    @Autowired
//    private IUserService userService;
//    @Autowired
//    private IGoodsService goodsService;
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    private ThymeleafViewResolver thymeleafViewResolver;
//
//    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
//    @ResponseBody
//    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
////        log.info(ticket);
////        if (StringUtils.isEmpty(ticket)) {
////            return "login";
////        }
////        User user = (User) session.getAttribute(ticket);
////        User user = userService.getUserByCookie(ticket, request, response);
////        if (user == null) {
////            return "login";
////        }
//        //Redis中获取页面，如果不为空，直接返回页面
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//        String html = (String) valueOperations.get("goodsList");
//        if (!StringUtils.isEmpty(html)) {
//            return html;
//        }
//        List<GoodsVo> goodsVoList = goodsService.findGoodsVo();
//        model.addAttribute("user", user);
//        model.addAttribute("goodsList", goodsVoList);
//        //如果为空，手动渲染，存入redis并返回
//        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//        String html1 = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
//        if (!StringUtils.isEmpty(html1)) {
//            valueOperations.set("goodsList", html1, 60, TimeUnit.SECONDS);
//        }
//        return html1;
//    }
//
//    @RequestMapping(value = "/toDetail2/{goodsId}", produces = "text/html;charset=utf-8")
//    @ResponseBody
//    public String toDetail2(Model model, User user, @PathVariable Long goodsId, HttpServletRequest request, HttpServletResponse response) {
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
//        if (!StringUtils.isEmpty(html)) {
//            return html;
//        }
//        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//        Date startDate = goodsVo.getStartDate();
//        Date endDate = goodsVo.getEndDate();
//        Date nowDate = new Date();
//        int secKillStatus = 0;
//        int remainSeconds = 0;
//        if (nowDate.before(startDate)) {
//            remainSeconds = (int) (startDate.getTime() - nowDate.getTime()) / 1000;
//        } else if (nowDate.after(endDate)) {
//            secKillStatus = 2;
//            remainSeconds = -1;
//        } else {
//            secKillStatus = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("user", user);
//        model.addAttribute("remainSeconds", remainSeconds);
//        model.addAttribute("secKillStatus", secKillStatus);
//        model.addAttribute("goods", goodsVo);
//        //如果为空，手动渲染，存入redis并返回
//        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//        String html1 = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
//        if (!StringUtils.isEmpty(html1)){
//            valueOperations.set("goodsDetail:"+goodsId, html1,60, TimeUnit.SECONDS);
//        }
//        return html1;
//    }
//
//    @RequestMapping(value = "/toDetail/{goodsId}", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId, HttpServletRequest request, HttpServletResponse response) {
//        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//        Date startDate = goodsVo.getStartDate();
//        Date endDate = goodsVo.getEndDate();
//        Date nowDate = new Date();
//        int secKillStatus = 0;
//        int remainSeconds = 0;
//        if (nowDate.before(startDate)) {
//            remainSeconds = (int) (startDate.getTime() - nowDate.getTime()) / 1000;
//        } else if (nowDate.after(endDate)) {
//            secKillStatus = 2;
//            remainSeconds = -1;
//        } else {
//            secKillStatus = 1;
//            remainSeconds = 0;
//        }
//        DetailVo detailVo = new DetailVo();
//        detailVo.setUser(user);
//        detailVo.setGoodsVo(goodsVo);
//        log.info(detailVo.getGoodsVo().getGoodsImg());
//        detailVo.setSeckillStatus(secKillStatus);
//        detailVo.setRemainSeconds(remainSeconds);
////        model.addAttribute("user", user);
////        model.addAttribute("remainSeconds", remainSeconds);
////        model.addAttribute("secKillStatus", secKillStatus);
////        model.addAttribute("goods", goodsVo);
//        return RespBean.success(detailVo);
//    }
//}
