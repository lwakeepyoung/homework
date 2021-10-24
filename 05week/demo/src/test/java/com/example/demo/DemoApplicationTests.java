package com.example.demo;

import com.lwa.DemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

	@Autowired
	private com.lwa.jdbc.demo.Test test;

	@Test
	void contextLoads() {
	}

	@Test
	public void test() throws Exception {
		test.test();
	}

}
