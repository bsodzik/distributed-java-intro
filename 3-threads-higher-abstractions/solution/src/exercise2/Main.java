package exercise2;

import exercise2.equipment.Brushes;
import exercise2.equipment.Paints;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Paints paints = new Paints();
        Brushes brushes = new Brushes();

        ExecutorService executors = Executors.newCachedThreadPool();
        for (int i = 0; i < 32; ++i) {
            executors.execute(new Painter(paints, brushes));
        }
        executors.shutdown();
        executors.awaitTermination(30, TimeUnit.SECONDS);
    }
}
