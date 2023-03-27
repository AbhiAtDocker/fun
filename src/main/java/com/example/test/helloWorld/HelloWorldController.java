package com.example.test.helloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/helloWorld")
	public String helloWorld() {
		return "hello world!";
	}
	
	
	@GetMapping(path="/helloWorldBean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world!");
	}
	
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format(name));
	}
	
	@GetMapping(path="/helloWorldInternationalization")
	public String  helloWorldInternationalization(
			@RequestHeader(name="Accept-Language", required=false)Locale locale) {
		return "hello world!";
	}
	
	
	
}
