package exercise1.equipment;

public class Brushes {
    private int available = 3;

    public void takeBrush() throws InterruptedException {
        if (available == 0) {
            throw new IllegalStateException("There are no more brushes!");
        }
        available -= 1;
    }

    public void returnBrush() {
        available += 1;
    }
}
