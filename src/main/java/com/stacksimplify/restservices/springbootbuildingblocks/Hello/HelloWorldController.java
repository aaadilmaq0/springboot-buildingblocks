package com.stacksimplify.restservices.springbootbuildingblocks.Hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
//	@RequestMapping(method = RequestMethod.GET, value= "/helloWorld")
	@GetMapping("/helloWorld")
	public String helloWord() {
		return "Hello World!";
	}
	
	@GetMapping("/bean")
	public UserDetails bean() {
		UserDetails details =  new UserDetails("Adil","Maqusood","Gorakhpur");
		return details;
	}

}