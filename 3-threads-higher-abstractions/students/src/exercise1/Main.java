package exercise1;

import exercise1.equipment.Brushes;
import exercise1.equipment.Paints;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Paints paints = new Paints();
        Brushes brushes = new Brushes();

        ExecutorService executors = Executors.newCachedThreadPool();
        for (int i = 0; i < 12; ++i) {
            executors.execute(new Painter(paints, brushes));
        }
        executors.shutdown();
        executors.awaitTermination(30, TimeUnit.SECONDS);
    }
}
