package exercise3;

import exercise2.MyThread;

public class Main {

    public static void main(String[] args) {
    	Thread[] threads = new Thread[4];
    	for (int i = 0; i < 4; i++)
    		threads[i] = new Thread(new MyRunnable(), "thread"+i);
    	for (int i = 0; i < 4; i++)
    	{
    		threads[i].start();
    	}

    	
    	while(true)
    	{
    		Boolean isAlive = false;
        	for (int i = 0; i < 4; i++)
        		isAlive |= threads[i].isAlive();
        	if (!isAlive)
        	{
        		System.out.println("FINISHED!");
        		break;
        	}
    	}
    	for (int i = 0; i < 4; i++)
    	{
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
}
