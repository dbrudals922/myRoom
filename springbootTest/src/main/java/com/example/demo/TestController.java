package com.ykm.portfolio;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {
	
	@RequestMapping("/")
	public String test() {
		return "";
	}
}
