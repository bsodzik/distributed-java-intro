package exercise7.equipment;

public class Paints {

    private int available = 3;

    public synchronized void takePaint() throws InterruptedException {
        if (available == 0) {
            throw new IllegalStateException("There are no more paints!");
        }
        available -= 1;
    }

    public synchronized void returnPaint() {
        available += 1;
    }
}
