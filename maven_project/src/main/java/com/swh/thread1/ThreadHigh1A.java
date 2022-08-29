package com.swh.thread1;

public class ThreadHigh1A extends Thread{
	
	private int sum = 0;
	private int count = 0;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(this){
			while(true) {
				count++;
				sum += count;
				if(count % 5 == 0) {
					break;
				}
				System.out.println(count + "ThreadA 쓰레드가 동작합니다.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			notify();
		}
	}
	
	public int getSum() {
		return sum;
	}
}
