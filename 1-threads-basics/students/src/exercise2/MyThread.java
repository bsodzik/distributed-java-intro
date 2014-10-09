package exercise2;

public class MyThread extends Thread {
	public MyThread(String name) {
		super(name);
	}
	
	@Override
	public void run()
	{
		for (int i = 0; i < 10; i++)
		{
			System.out.print(this.getName()+'\n');
			try {
				Thread.sleep(10);

			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		super.run();
	}
}
