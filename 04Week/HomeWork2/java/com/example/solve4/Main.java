package com.example.solve4;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        // Wrapper对象持有变量存储结果值，线程间传递对象引用
        // 主线程通过CyclicBarrier等待子线程运行完成
        Wrapper wrapper = new Wrapper();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        ThreadProducer th = new ThreadProducer(wrapper, cyclicBarrier);
        th.start();
        cyclicBarrier.await();
        System.out.println("main thread:" + wrapper.getN());
    }

    public static class ThreadProducer extends Thread{

        private Wrapper wrapper;
        private CyclicBarrier cyclicBarrier;

        public ThreadProducer(Wrapper wrapper, CyclicBarrier cyclicBarrier) {
            this.wrapper = wrapper;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                wrapper.setN(new Random().nextInt(10));
                System.out.println("child thread:" + wrapper.getN());
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
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
