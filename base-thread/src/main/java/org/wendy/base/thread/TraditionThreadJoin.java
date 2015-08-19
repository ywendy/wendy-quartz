package org.wendy.base.thread;

public class TraditionThreadJoin {

	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread(new A());
		Thread t2 = new Thread(new B());
		t1.start();
		t1.join();// 注释掉此行，则t1线程会被t2线程的运行结果打乱，即：t2 的结果会和t1 的结果混合
		t2.start();
		t2.join();
		
		//test 

	}

}

class A implements Runnable {

	@Override
	public void run() {
		for (int i = 1; i <= 10; i++) {
			System.out.println(Thread.currentThread().getName() + " A sequence of " + i);
		}
	}

}

class B implements Runnable {

	@Override
	public void run() {
		for (int i = 1; i <= 100; i++) {
			System.out.println(" B sequence of " + i);
		}
	}

}
