package com.example.solve11;

import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 提交FutureTask，等待获取结果
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Producer producer = new Producer();
        FutureTask<Integer> futureTask = new FutureTask(producer);
        executorService.submit(futureTask);
        System.out.println(futureTask.get());
        executorService.shutdown();
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
