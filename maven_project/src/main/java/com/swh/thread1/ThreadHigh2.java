package com.swh.thread1;

public class ThreadHigh2 extends Thread{
	
	private int sum = 0;
	private ThreadHigh2SubJob otherThread;
	
	public ThreadHigh2(ThreadHigh2SubJob otherThread) {
		// TODO Auto-generated constructor stub
		this.otherThread = otherThread;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(this){
			for(int i=1; i<5; i++) {
				sum += i;
				System.out.println(i + "상위 쓰레드가 동작합니다.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				this.otherThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sum += this.otherThread.getSum();
			notify();
		}
	}
	
	public int getSum() {
		return sum;
	}
}
