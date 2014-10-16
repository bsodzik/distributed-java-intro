package exercise5;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        VolatileTask task = new VolatileTask();
        Thread thread = new Thread(task);
        thread.start();
        Thread.sleep(100);
        task.end();
    }
}
