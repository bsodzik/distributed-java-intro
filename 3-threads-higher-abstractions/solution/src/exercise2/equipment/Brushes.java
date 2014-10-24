package exercise2.equipment;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Brushes {

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);

    public Brushes() {
        queue.offer("regular");
        queue.offer("triangular");
        queue.offer("spectacular");
    }

    public String takeBrush() throws InterruptedException {
        return queue.take();
    }

    public void returnBrush(String brush) {
        queue.offer(brush);
    }
}
