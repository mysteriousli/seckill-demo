package com.example.seckilldemo.utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

interface Interceptor{
    Object plugin(Object object, InterceptorChain chain);
}
class InterceptorA implements Interceptor{

    @Override
    public Object plugin(Object object, InterceptorChain chain) {
        System.out.println("InterceptorA");
        return chain.plugin(object);
    }
}
class InterceptorB implements Interceptor{

    @Override
    public Object plugin(Object object, InterceptorChain chain) {
        System.out.println("InterceptorB");
        return chain.plugin(object);
    }
}
class InterceptorC implements Interceptor{

    @Override
    public Object plugin(Object object, InterceptorChain chain) {
        System.out.println("InterceptorC");
        return chain.plugin(object);
    }
}
class InterceptorChain{
    private List<Interceptor> interceptorList = new ArrayList<>();
    private Iterator<Interceptor> iterator = null;
    public void addInterceptor(Interceptor interceptor){
        interceptorList.add(interceptor);
    }
    public Object plugin(Object target){
        if (iterator == null){
            iterator = interceptorList.iterator();
        }
        if (iterator.hasNext()){
            Interceptor next = iterator.next();
            next.plugin(target,this);
        }
        return target;
    }
}
public class Test {
    public static void main(String[] args) {
        InterceptorA interceptorA = new InterceptorA();
        InterceptorB interceptorB = new InterceptorB();
        InterceptorC interceptorC = new InterceptorC();
        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(interceptorA);
        interceptorChain.addInterceptor(interceptorB);
        interceptorChain.addInterceptor(interceptorC);
        interceptorChain.plugin(new Object());
    }
}
