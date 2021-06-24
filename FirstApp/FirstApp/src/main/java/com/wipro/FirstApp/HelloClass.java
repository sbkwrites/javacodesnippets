package com.wipro.FirstApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloClass {
	
	@RequestMapping("/")
	public String sayHello() {
		return "home";
	}
	
	
	@RequestMapping("/data")
	public String sayData() {
		
		return "cookoo";
	}

}
