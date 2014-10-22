package exercise1;

import exercise1.equipment.Brushes;
import exercise1.equipment.Paints;

public class Painter implements Runnable {

    private final Paints paints;
    private final Brushes brushes;

    public Painter(Paints paints, Brushes brushes) {
        this.paints = paints;
        this.brushes = brushes;
    }

    @Override
    public void run() {
        try {
            paints.takePaint();
            Thread.sleep(100);
            brushes.takeBrush();
            Thread.sleep(100);

            System.out.println("Painter " + Thread.currentThread().getName() + " is painting!");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            paints.returnPaint();
            brushes.returnBrush();
        }
    }
}
