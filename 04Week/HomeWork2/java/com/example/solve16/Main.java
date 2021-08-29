package com.example.solve16;

import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // CompletableFuture计算后获取结果
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            int n = new Random().nextInt(10);
            System.out.println("child thread:" + n);
            return n;
        });
        System.out.println("main thread:" + completableFuture.get());
    }

}
