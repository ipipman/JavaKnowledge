package com.ipipman.gof.example.singleton;

import java.util.concurrent.atomic.AtomicLong;

//  5. 枚举
//  基于 **枚举类型** 的单例实现，这种实现方式通过 Java 枚举类型本身的特性，保证了实例创建的线程安全性和实例的唯一性；
public enum IdGenerator5 {

    INSTANCE;
    private AtomicLong id = new AtomicLong(0);

    public Long getId() {
        return id.incrementAndGet();
    }
}
