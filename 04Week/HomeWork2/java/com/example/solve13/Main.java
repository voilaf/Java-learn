package com.example.solve13;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        // 管道通信，主线程read时堵塞，等待子线程write
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader();
        pipedReader.connect(pipedWriter);

        ThreadProducer th = new ThreadProducer(pipedWriter);
        th.start();
        System.out.println("main thread:" + pipedReader.read());
        pipedReader.close();
    }

    public static class ThreadProducer extends Thread{

        private PipedWriter pipedWriter;

        public ThreadProducer(PipedWriter pipedWriter) {
            this.pipedWriter = pipedWriter;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                int n = new Random().nextInt(10);
                System.out.println("child thread:" + n);
                pipedWriter.write(n);
                pipedWriter.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
