package exercise1.equipment;

public class Paints {
    private int available = 3;

    public void takePaint() throws InterruptedException {
        if (available == 0) {
            throw new IllegalStateException("There are no more paints!");
        }
        available -= 1;
    }

    public void returnPaint() {
        available += 1;
    }
}
