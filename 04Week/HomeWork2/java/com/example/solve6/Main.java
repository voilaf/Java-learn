package com.example.solve6;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Wrapper对象持有变量存储结果值，线程间传递对象引用
        // 主线程通过Lock的await方法堵塞，等待子线程运行完成时signal触发
        Wrapper wrapper = new Wrapper();
        Lock lock = new ReentrantLock();
        lock.lock();
        Condition condition = lock.newCondition();
        ThreadProducer th = new ThreadProducer(wrapper, lock, condition);
        th.start();
        condition.await();
        lock.unlock();
        System.out.println("main thread:" + wrapper.getN());
    }

    public static class ThreadProducer extends Thread{

        private Wrapper wrapper;
        private Lock lock;
        private Condition condition;

        public ThreadProducer(Wrapper wrapper, Lock lock, Condition condition) {
            this.wrapper = wrapper;
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                lock.lock();
                wrapper.setN(new Random().nextInt(10));
                System.out.println("child thread:" + wrapper.getN());
                condition.signalAll();
                lock.unlock();
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
