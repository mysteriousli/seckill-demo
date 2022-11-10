package com.example.seckilldemo.utils;

import com.example.seckilldemo.pojo.SeckillOrder;
import com.example.seckilldemo.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

interface TestMapper {
    @Select("select * from user where id=#{id}")
    String testList(int id);
}

public class Main {
    //反射
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
//        Constructor constructor = User.class.getConstructor();
//        Object obj = constructor.newInstance();
//        System.out.println(obj);
//
//        Class clzz = Class.forName("com.example.seckilldemo.pojo.User");
//        Object obj1 = clzz.getConstructor().newInstance();
//        System.out.println(obj1);
//
//        Class clzz1 = User.class;
//        Object obj2 = clzz1.getConstructor().newInstance();
//        System.out.println(obj2);
//
//        User user = new User();
//        Class clzz2 = user.getClass();
//        Object obj3 = clzz2.getConstructor().newInstance();
//        System.out.println(obj3);
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/user.xml");
//        System.out.println(applicationContext.getBean(SeckillOrder.class).getOrderId());
        //注册
        TestMapper testMapper = (TestMapper) Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{TestMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //语句解析
                Select annotation = method.getAnnotation(Select.class);
                Map<String, Object> map = buliderMethodArgNameMap(method, args);
                if (annotation != null) {
                    String[] value = annotation.value();
                    String sql = value[0];
                    sql = parseSql(sql, map);
                    System.out.println(sql);
                }
                //结果映射
                return null;
            }
        });
        testMapper.testList(1);
    }

    //    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
//        for (String beanDefinitionName: beanDefinitionNames){
//            System.out.println(beanDefinitionName);
//            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
//            beanDefinition.setDependsOn();
//        }
//    }
    public static String parseSql(String sql, Map<String, Object> nameMap) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = sql.length();
        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);
            if (c == '#') {
                int nextIndex = i + 1;
                char nextChar = sql.charAt(nextIndex);
                if (nextChar != '{') {
                    throw new RuntimeException(String.format("这里应该为#{\nsql:%s\nindex:%d}", stringBuilder.toString(), nextIndex));
                }
                StringBuilder argSB = new StringBuilder();
                i = pareSqlArg(argSB, sql, nextIndex);
                String argName = argSB.toString();
                Object argValue = nameMap.get(argName);
                stringBuilder.append(argValue.toString());
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static int pareSqlArg(StringBuilder argSB, String sql, int nextIndex) {
        nextIndex++;
        for (; nextIndex < sql.length(); nextIndex++) {
            char c = sql.charAt(nextIndex);
            if (sql.charAt(nextIndex) != '}') {
                argSB.append(c);
                continue;
            }
            if (c == '}') {
                return nextIndex;
            }
        }
        throw new RuntimeException(String.format("缺少左括号和右括号！"));
    }

    public static Map<String, Object> buliderMethodArgNameMap(Method method, Object[] args) {
        Map<String, Object> nameArgMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        int index[] = {0};
        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            nameArgMap.put(name, args[index[0]]);
            index[0]++;
        });
        return nameArgMap;
    }
}