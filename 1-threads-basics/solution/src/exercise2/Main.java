package exercise2;

public class Main {

    public static void main(String[] args) {
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread("Thread-" + i);
        }

        System.out.println("Single threaded output:");
        for (Thread thread : threads) {
            thread.run(); // Invocation of run does not create new thread
        }

        System.out.println("Multi threaded output:");
        for (Thread thread : threads) {
            thread.start(); // To create new thread start must be invoked
        }
    }
}
