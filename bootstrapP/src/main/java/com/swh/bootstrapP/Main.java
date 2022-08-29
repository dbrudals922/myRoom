package com.swh.bootstrapP;

import static spark.Spark.get;
import static spark.Spark.modelAndView;

import java.util.HashMap;
import java.util.Map;

import spark.Spark;

public class Main {
	
	public static void main(String args[]){

		Spark.staticFileLocation("/modern");
		
		// Gets the book resource for the provided id
		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello FreeMarker World");
			
			// 메인페이지 -> 기분(python/colab)
			// 영화랭킹

			// The hello.ftl file is located in directory:
			// src/test/resources/spark/examples/templateview/freemarker
			return modelAndView(attributes, "index.html");
		}, new FreeMarkerTemplateEngine());


	}
}