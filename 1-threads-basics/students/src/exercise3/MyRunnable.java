package exercise3;

public class MyRunnable implements Runnable {

	public void run()
	{
		for (int i = 0; i < 10; i++)
		{
			System.out.print(Thread.currentThread().getName()+'\n');
		}
	}
}
