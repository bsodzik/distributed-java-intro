package exercise2;

import exercise2.equipment.Brushes;
import exercise2.equipment.Paints;

import java.util.concurrent.ThreadLocalRandom;

public class Painter implements Runnable {

    private final Paints paints;
    private final Brushes brushes;

    public Painter(Paints paints, Brushes brushes) {
        this.paints = paints;
        this.brushes = brushes;
    }

    @Override
    public void run() {
        String paint = null;
        String brush = null;

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200));
            paint = paints.takePaint();

            Thread.sleep(ThreadLocalRandom.current().nextInt(200));
            brush = brushes.takeBrush();

            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
            System.out.printf("Painter %s is painting with %s paint and %s brush\n",
                    Thread.currentThread().getName(), paint, brush);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (paint != null) {
                paints.returnPaint(paint);
            }
            if (brush != null) {
                brushes.returnBrush(brush);
            }
        }
    }
}
