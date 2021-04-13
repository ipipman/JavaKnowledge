package com.ipipman.gof.example.singleton;

// 饿汉式
// 饿汉式的实现比较简单，在类加载的时候，instance 静态实例已经创建并初始化好了，所以 instance 实例是线程安全的；
// 不过，这样实现方式不支持延迟加载，初始化时可能会耗性能；

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator1 {

    private AtomicLong id = new AtomicLong(0);
    private static final IdGenerator1 instance = new IdGenerator1();

    private IdGenerator1() {

    }

    public static IdGenerator1 getInstance() {
        return instance;
    }

    public Long getId(){
        return id.incrementAndGet();
    }

    // TODO Testing...
    public static void main(String[] args) {
        IdGenerator1 idGenerator1 = IdGenerator1.getInstance();
        System.out.println(idGenerator1.getId());
    }
}
