package com.ipipman.gof.example.foobar;

import javafx.scene.SubScene;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by ipipman on 2021/5/13.
 *
 * @version V1.0
 * @Package com.ipipman.gof.example.forbar
 * @Description: (用一句话描述该文件做什么)
 * @date 2021/5/13 6:44 下午
 */
public class Foobar {

    private final int n;

    private Semaphore foo = new Semaphore(1);
    private Semaphore bar = new Semaphore(0);

    Foobar(int n) {
        this.n = n;
    }

    public void printFoo(Runnable r) {
        for (int i = 0; i <= n; i++) {
            try {
                foo.acquire();
                r.run();
                bar.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printBar(Runnable r) {
        for (int i = 0; i <= n; i++) {
            try {
                bar.acquire();
                r.run();
                foo.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Foobar foobar = new Foobar(10);
        Thread t1 = new Thread(() -> {
            foobar.printFoo(new Runnable() {
                public void run() {
                    System.out.print("foo");
                }
            });
        });

        Thread t2 = new Thread(() -> {
            foobar.printBar(() -> {
                System.out.print("bar\n");
            });
        });
        t1.start();
        t2.start();
    }


}
