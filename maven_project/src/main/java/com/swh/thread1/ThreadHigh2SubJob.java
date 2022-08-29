package com.swh.thread1;

public class ThreadHigh2SubJob extends Thread{
	
	private int sum = 0;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(this){
			for(int i=5; i<11; i++) {
				sum += i;
				System.out.println(i + "하위 쓰레드가 동작합니다.");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getSum() {
		return sum;
	}
}