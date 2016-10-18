package exercise4;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
    	ExecutorService executorService = Executors.newFixedThreadPool(4);

    	for (int i = 0; i < 4; i++)
    		executorService.execute(new MyRunnable());

		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
		}
		System.out.println("FINISHED!");
    }
}
