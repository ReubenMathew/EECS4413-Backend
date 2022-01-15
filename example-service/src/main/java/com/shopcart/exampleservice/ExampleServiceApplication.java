package com.shopcart.exampleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ExampleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleServiceApplication.class, args);
	}

	@GetMapping("/add")
	public String add(@RequestParam(value="num1") int num1, @RequestParam(value="num2") int num2) {
		int sum = Arithmetic.addTwoNumbers(num1, num2);
		return String.format("%d", sum);
	}

}
