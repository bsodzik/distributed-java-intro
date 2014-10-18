package exercise3;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MyRunnable(), "Thread-" + i);
        }

        System.out.println("Single threaded output:");
        for (Thread thread : threads) {
            thread.run(); // Invocation of run does not create new thread
        }

        System.out.println("Multi threaded output:");
        for (Thread thread : threads) {
            thread.start(); // To create new thread start must be invoked
        }

        // Detecting when all threads finished their jobs
        //detectEndUsingNaive();
        //detectEndUsingPolling(threads);
        detectEndUsingJoin(threads);
    }

    private static void detectEndUsingNaive() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("FINISHED");
    }

    private static void detectEndUsingPolling(Thread[] threads) throws InterruptedException {
        while (true) {
            boolean isAlive = false;
            Thread.sleep(5);
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    isAlive = true;
                    break;
                }
            }
            if (!isAlive) {
                break;
            }
        }
        System.out.println("FINISHED");
    }

    private static void detectEndUsingJoin(Thread[] threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("FINISHED");
    }
}
