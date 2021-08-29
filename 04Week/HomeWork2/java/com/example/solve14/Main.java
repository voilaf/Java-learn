package com.example.solve14;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;

public class Main {

    public static volatile Integer num = null;

    public static void main(String[] args) throws IOException {
        // 主线程循环判断静态变量是否为null，子线程写入静态变量
        ThreadProducer th = new ThreadProducer();
        th.start();
        while (num == null) {}
        System.out.println("main thread:" + num);
    }

    public static class ThreadProducer extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                int n = new Random().nextInt(10);
                System.out.println("child thread:" + n);
                num = new Integer(n);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
