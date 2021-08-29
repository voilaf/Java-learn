package com.example.sovle8;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Wrapper对象持有变量存储结果值，线程间传递对象引用
        // 主线程循环判断本身的中断状态，子线程完成任务后对主线程发送一个中断信号
        Wrapper wrapper = new Wrapper();
        Thread current = Thread.currentThread();
        ThreadProducer th = new ThreadProducer(wrapper, current);
        th.start();

        while (!current.isInterrupted()) { }
        System.out.println("main thread:" + wrapper.getN());
    }

    public static class ThreadProducer extends Thread{

        private Wrapper wrapper;
        private Thread mainThread;

        public ThreadProducer(Wrapper wrapper, Thread mainThread) {
            this.wrapper = wrapper;
            this.mainThread = mainThread;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                wrapper.setN(new Random().nextInt(10));
                System.out.println("child thread:" + wrapper.getN());
                mainThread.interrupt();
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
