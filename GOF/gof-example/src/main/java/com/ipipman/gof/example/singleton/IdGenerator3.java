package com.ipipman.gof.example.singleton;

import java.util.concurrent.atomic.AtomicLong;

//  3.双重检测
//  饿汉式不支持延迟加载，懒汉式有性能问题，不支持高并发；
//  双重检测 兼顾了性能和延迟加载问题；
//  在实现方式中，只要 instance 被创建后，即便再调用 getInstance() 函数也不会再进入到加锁逻辑中了；

public class IdGenerator3 {

    private static volatile IdGenerator3 instance = null;

    private int generatorId = 0;

    private IdGenerator3(){

    }

    public static IdGenerator3 getInstance(){
        if (instance == null){
            synchronized (IdGenerator3.class){
                if (instance == null){
                    instance = new IdGenerator3();
                }
            }
        }
        return instance;
    }

    public int getGeneratorId(){
        return generatorId++;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(IdGenerator3.getInstance().getGeneratorId());
        }

    }

}
