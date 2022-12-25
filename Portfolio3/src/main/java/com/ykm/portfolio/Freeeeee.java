package com.ykm.portfolio;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Freeeeee {
	public static void main(String[] args) {
		try {
		Object ob = new JSONParser().parse(new FileReader("questions.json"));
		JSONObject js = (JSONObject) ob;

		String city = (String) js.get("value");
		System.out.println(city);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
