package main;

import static spark.Spark.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String args[]) throws IOException {
		
		staticFileLocation("/amoeba");

		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello FreeMarker World");
			

			// The hello.ftl file is located in directory:
			// src/test/resources/spark/examples/templateview/freemarker
			return modelAndView(attributes, "amoeba.html");
		}, new FreeMarkerTemplateEngine());

	}
}
