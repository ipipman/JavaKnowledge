package com.ipipman.gof.example.foobar;

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

    private int n;

    Semaphore foo = new Semaphore(1);
    Semaphore bar = new Semaphore(0);

    public Foobar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.acquire();
            printFoo.run();
            bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.acquire();
            printBar.run();
            foo.release();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Foobar foobar = new Foobar(10);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    foobar.foo(() -> {
                        System.out.print("foo");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    foobar.bar(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("bar\n");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
    }
}
