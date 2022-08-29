package com.swh.thread1;

public class ThreadHigh1SecondMain {

	public static void main(String[] args) {
		ThreadHigh1A threadA = new ThreadHigh1A();
		threadA.start();
		ThreadHigh1A threadA2 = new ThreadHigh1A();
		threadA2.start();
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
		//	아래 코드에서 이상한 점은 없나요?
		synchronized (threadA2) {
			System.out.println("ThreadA 가 완료될때까지 Wait...");
			try {
				threadA2.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("5의 배수일때까지 합산은 : " + threadA2.getSum());
		}
	}
}
