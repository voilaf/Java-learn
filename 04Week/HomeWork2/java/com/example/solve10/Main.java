package com.example.solve10;

import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 提交Callable，返回Future，等待获取结果
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(new Producer());
        System.out.println(future.get());
        executor.shutdown();
    }

    public static class Producer implements Callable {

        @Override
        public Integer call() {
            try {
                Thread.sleep(1000);
                return new Random().nextInt(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
