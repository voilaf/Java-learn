package com.example.solve5;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Wrapper对象持有变量存储结果值，线程间传递对象引用
        // 主线程通过同步块并wait，等待子线程运行完成时notify触发
        Wrapper wrapper = new Wrapper();
        ThreadProducer th = new ThreadProducer(wrapper);
        synchronized (wrapper) {
            th.start();
            wrapper.wait();
            System.out.println("main thread:" + wrapper.getN());
        }
    }

    public static class ThreadProducer extends Thread{

        private Wrapper wrapper;

        public ThreadProducer(Wrapper wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public void run() {
            synchronized (wrapper) {
                try {
                    Thread.sleep(1000);
                    wrapper.setN(new Random().nextInt(10));
                    System.out.println("child thread:" + wrapper.getN());
                    wrapper.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
