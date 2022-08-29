package com.swh.thread1;


public class ThreadHigh1Main {
	
	public static void main(String[] args) {
		ThreadHigh1A threadA = new ThreadHigh1A();
		threadA.start();
		//	아래 주석코드를 풀고 다시 실행해보세요.
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		synchronized (threadA) {
			System.out.println("ThreadA 가 완료될때까지 Wait...");
			try {
				threadA.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("5의 배수일때까지 합산은 : " + threadA.getSum());
		}
	}
}
