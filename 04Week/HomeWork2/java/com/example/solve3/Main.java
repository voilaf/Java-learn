package com.example.solve3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Wrapper对象持有变量存储结果值，线程间传递对象引用
        // 主线程通过CountDownLatch等待子线程运行完成
        Wrapper wrapper = new Wrapper();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ThreadProducer th = new ThreadProducer(wrapper, countDownLatch);
        th.start();
        countDownLatch.await();
        System.out.println("main thread:" + wrapper.getN());
    }

    public static class ThreadProducer extends Thread{

        private Wrapper wrapper;
        private CountDownLatch countDownLatch;

        public ThreadProducer(Wrapper wrapper, CountDownLatch countDownLatch) {
            this.wrapper = wrapper;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                wrapper.setN(new Random().nextInt(10));
                System.out.println("child thread:" + wrapper.getN());
                countDownLatch.countDown();
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
