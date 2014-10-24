package exercise2.equipment;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Paints {

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);

    public Paints() {
        queue.offer("red");
        queue.offer("green");
        queue.offer("blue");
    }

    public String takePaint() throws InterruptedException {
        return queue.take();
    }

    public void returnPaint(String paint) {
        queue.offer(paint);
    }
}
