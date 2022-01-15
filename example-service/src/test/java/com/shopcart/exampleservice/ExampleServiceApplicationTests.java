package com.shopcart.exampleservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExampleServiceApplicationTests {

	@Test
	void testAdd() {
		assertEquals(Arithmetic.addTwoNumbers(2, 3), 5);
	}

}
