package com.example.solve9;

import java.util.Random;
import java.util.concurrent.locks.LockSupport;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Wrapper对象持有变量存储结果值，线程间传递对象引用
        // 主线程通过LockSupport堵塞，子线程任务完成后调用LockSupport.unpark()解锁主线程
        Wrapper wrapper = new Wrapper();
        Thread current = Thread.currentThread();
        ThreadProducer th = new ThreadProducer(wrapper, current);
        th.start();

        LockSupport.park();
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(mainThread);
//            mainThread.interrupt();
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
