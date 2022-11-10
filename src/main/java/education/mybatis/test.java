package education.mybatis;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class test {
    public static void main(String[] args) {
        UserController userController = new UserController();
        Class clazz = userController.getClass();

        //获取所有属性
//        System.out.println(clazz.getDeclaredFields()[0].getName());
        Stream.of(clazz.getDeclaredFields()).forEach(field -> {
            //判断每一个属性是否有注解
            MyAutowired myAutowired = field.getAnnotation(MyAutowired.class);
            if (myAutowired != null) {
                field.setAccessible(true);
                Class clazz1 = field.getType();
                //实例化属性对象
                Object o = null;
                try {
                    o = clazz1.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    field.set(userController, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(userController.getUserService());
    }

}
