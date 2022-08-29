package com.swh.thread1;




public class ThreadHigh2Main {
	
	
	public static void main(String[] args) {
		ThreadHigh2SubJob subThread = new ThreadHigh2SubJob();
		subThread.start();
		ThreadHigh2 threadA = new ThreadHigh2(subThread);
		threadA.start();
		synchronized (threadA) {
			System.out.println("상위 가 완료될때까지 Wait...");
			try {
				threadA.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("1~10까지 합산은 : " + threadA.getSum());
		}
	}
}