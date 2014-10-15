package exercise5;

public class VolatileTask implements Runnable {

    private boolean isRunning = true;

    @Override
    public void run() {
        long counter = 0;
        while (isRunning) {
            counter += 1;
        }
        System.out.println("Counter: " + counter);
    }

    public void end() {
        isRunning = false;
    }
}
