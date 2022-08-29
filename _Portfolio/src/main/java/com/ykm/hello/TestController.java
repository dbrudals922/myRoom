package com.ykm.hello;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
	
	@GetMapping("/hello")
	public String test() {
		return "hello";
	}
	
	@RequestMapping("/movieRanking.html")
	public String movieRanking(Model model, @RequestParam(value = "date", required=false)String date) {
		
		model.addAttribute("values", getValues(date));
		model.addAttribute("searchDate", date);
		
		return "movieRanking";
	}
	
	@RequestMapping("/bus-info.html")
	public String busInfo(Model model, @RequestParam(value = "local", required=false) String local, @RequestParam(value = "number", required=false) String busNumber) {
		
		BusApi busInfo = new BusApi();
		List<Map<String, String>> busesList = busInfo.getBusStep1(8);
		
		for (Map<String, String> bus : busesList) {
			System.out.println(bus);
		}
		
		return "bus-info";
	}
	
	private List<Map<String, String>> getValues(String date) {
    	return MovieRank.getMovieList(date);
    }
}
