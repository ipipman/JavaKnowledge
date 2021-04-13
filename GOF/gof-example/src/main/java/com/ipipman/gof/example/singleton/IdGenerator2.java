package com.ipipman.gof.example.singleton;

//  2.懒汉试
//  有饿汉式就有懒汉式；
//  懒汉式对于饿汉式的优势在于它支持延迟加载，不影响初始化性能；
//  不过懒汉试缺点也很明显，我们给 getInstance() 加了一个 synchronized 锁，导致这个方法并发度很低，性能非常差；

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator2 {

    private AtomicLong id = new AtomicLong(0);
    private static IdGenerator2 instance;

    private IdGenerator2() {

    }

    public static synchronized IdGenerator2 getInstance() {
        if (instance == null) {
            instance = new IdGenerator2();
        }
        return instance;
    }

    public Long getId() {
        return id.incrementAndGet();
    }

    // TODO Testing...
    public static void main(String[] args) {
        IdGenerator2 idGenerator2 = IdGenerator2.getInstance();
        System.out.println(idGenerator2.getId());
    }

}
