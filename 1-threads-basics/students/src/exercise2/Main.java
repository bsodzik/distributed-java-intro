package exercise2;

import exercise2.MyThread;

public class Main {

    public static void main(String[] args) {
    	MyThread[] threads = new MyThread[4];
    	for (int i = 0; i < 4; i++)
    		threads[i] = new MyThread("thread0"+i);
    	for (int i = 0; i < 4; i++)
    		threads[i].start();
    }
}
