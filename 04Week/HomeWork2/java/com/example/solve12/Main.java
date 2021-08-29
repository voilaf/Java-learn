package com.example.solve12;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 主线程通过BlockQueue堵塞，等待子线程生产数据
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(1);
        ThreadProducer th = new ThreadProducer(blockingQueue);
        th.start();
        System.out.println("main thread:" + blockingQueue.take());
    }

    public static class ThreadProducer extends Thread{

        private BlockingQueue blockingQueue;

        public ThreadProducer(BlockingQueue blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                int num = new Random().nextInt(10);
                System.out.println("child thread:" + num);
                blockingQueue.add(num);
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
