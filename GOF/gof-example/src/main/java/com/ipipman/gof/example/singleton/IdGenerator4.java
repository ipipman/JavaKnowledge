package com.ipipman.gof.example.singleton;

import java.util.concurrent.atomic.AtomicLong;

//  4. 静态内部类
//  静态内部类它有点类似于饿汉模式，但又能做到延迟加载；
//  SingletonHolder 是一个内部静态类，当外部类 IdGenerator4 被加载的时候，并不会创建  SingletonHodler 实例对象；
//  只有在 getInstance() 方式时，SingletonHodler 类才会被加载；
//  instance 的唯一性、创建过程的线程安全由 JVM 来保证；

public class IdGenerator4 {

    private AtomicLong id = new AtomicLong(0);

    private IdGenerator4() {

    }

    private static class SingletonHolder {
        public final static IdGenerator4 instance = new IdGenerator4();
    }

    public static IdGenerator4 getInstance() {
        return SingletonHolder.instance;
    }

    public Long getId() {
        return id.incrementAndGet();
    }

    // TODO Testing ...
    public static void main(String[] args) {
        IdGenerator4 idGenerator4 = IdGenerator4.getInstance();
        System.out.println(idGenerator4.getId());
    }
}
