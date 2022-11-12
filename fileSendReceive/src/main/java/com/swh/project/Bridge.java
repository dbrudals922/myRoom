package com.swh.project;

public class Bridge {
	static String a = null;
	static{
		a = "melong";
	}
	public static void main(String[] args) {
		String b = Bridge.a;
		System.out.println(b);
	}
}
