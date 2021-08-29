package com.example.solve15;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Wrapper对象持有变量存储结果值，线程间传递对象引用
        // 设置Semaphore初始为0，主线程调用acquire后会堵塞，
        // 子线程完成任务release，permits+1，主线程恢复执行
        Wrapper wrapper = new Wrapper();
        Semaphore semaphore = new Semaphore(0);
        ThreadProducer th = new ThreadProducer(wrapper, semaphore);
        th.start();
        semaphore.acquire();
        System.out.println("main thread:" + wrapper.getN());
    }

    public static class ThreadProducer extends Thread{

        private Wrapper wrapper;
        private Semaphore semaphore;

        public ThreadProducer(Wrapper wrapper, Semaphore semaphore) {
            this.wrapper = wrapper;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                wrapper.setN(new Random().nextInt(10));
                System.out.println("child thread:" + wrapper.getN());
                semaphore.release();
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
