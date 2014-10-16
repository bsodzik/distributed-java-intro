package exercise6;

import exercise6.equipment.Brush;
import exercise6.equipment.Paint;
import exercise6.painters.LeftHandedPainter;
import exercise6.painters.RightHandedPainter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Paint paint = new Paint();
        Brush brush = new Brush();

        ExecutorService executors = Executors.newCachedThreadPool();
        executors.execute(new LeftHandedPainter(paint, brush));
        executors.execute(new RightHandedPainter(paint, brush));

        executors.shutdown();
        executors.awaitTermination(10, TimeUnit.SECONDS);
        if (!executors.isTerminated()) {
            System.out.println("Some threads did not finish in 10 seconds!");
            System.out.println("Probably you have a deadlock in your code!");
        }
    }
}
