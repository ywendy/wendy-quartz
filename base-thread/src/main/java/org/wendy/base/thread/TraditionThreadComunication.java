package org.wendy.base.thread;

/**
 * <span> 子线程循环10次 ,主线程循环100次，接着子线程在循环10次，然后再主线程循环100次， 如此交替执行50次，结束.
 * 
 * </span> <b> 此方式可以实现两个线程的顺序执行. join 方法也可以实现这样的过程.<b>
 * 
 * @author tony
 *
 */
public class TraditionThreadComunication {

	public static void main(String[] args) {
		final Business bus = new Business();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					bus.sub(i);
				}
			}

		}).start();

		for (int i = 1; i <= 50; i++) {
			bus.main(i);
		}

	}

}

class Business {
	private boolean beShouldSub = true;

	// synchronized 放在方法上等同于 synchronized(object/this)
	// ,如果是静态方法，则为synchronized(Business.class)
	// synchronized 是给调用此方法的对象加监视器锁.
	public synchronized void sub(int j) {
		while (!beShouldSub) {// 防止spurious weak up 具体参见 Object 中的wait 方法注释.
			try {
				this.wait();// this 代表当前调用此方法的对象，指的是同一个Business 对象.
				// 判断是否应该到子线程运行，如果不是就wait()直到 主线程main方法调用 notify
				// （同一个对象）后，被唤醒，然后检查beshouldSub 是否为true
				// 如果为true 则执行下面的逻辑，此刻主线程就处于等待状态
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 1; i <= 10; i++) {
			System.out.println("sub thread sequence of  " + i + ", loop of " + j);
		}
		// 当子线程运行完后，把同一个变量设为了false ，然后唤醒了主线程，则主线程进行beshouldSub 判断，然后执行逻辑操作
		beShouldSub = false;
		this.notify();
	}

	public synchronized void main(int j) {
		while (beShouldSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 1; i <= 100; i++) {
			System.out.println("main thread sequence of " + i + ", loop of " + j);
		}
		beShouldSub = true;
		this.notify();
	}
}
