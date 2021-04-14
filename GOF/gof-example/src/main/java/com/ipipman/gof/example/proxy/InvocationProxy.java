package com.ipipman.gof.example.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// Java 1.3 动态代理
public class InvocationProxy implements InvocationHandler {

    private Object delegate;

    public Object bind(Object delegate) {
        this.delegate = delegate;
        ClassLoader classLoader = delegate.getClass().getClassLoader();
        Class<?>[] clazz = delegate.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, clazz, this::invoke);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            System.out.println("InvocationHandler start ...");
            result = method.invoke(delegate, args);
            System.out.println("InvocationHandler end ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // TODO Testing ...
    public static void main(String[] args) {
        InvocationProxy invocationProxy = new InvocationProxy();
        IPersonService personService = (IPersonService) invocationProxy.bind(new PersonServiceImpl());
        personService.doing();
    }
}