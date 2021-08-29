package com.example.solve7;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Wrapper对象持有变量存储结果值，线程间传递对象引用
        // 主线程判断子进程活跃状态，若未完成则本身yield
        Wrapper wrapper = new Wrapper();
        ThreadProducer th = new ThreadProducer(wrapper);
        th.start();
        while (th.isAlive()) {
            Thread.currentThread().yield();
        }
        System.out.println("main thread:" + wrapper.getN());
    }

    public static class ThreadProducer extends Thread{

        private Wrapper wrapper;

        public ThreadProducer(Wrapper wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                wrapper.setN(new Random().nextInt(10));
                System.out.println("child thread:" + wrapper.getN());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Wrapper {

        private int n;

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }
    }
}
