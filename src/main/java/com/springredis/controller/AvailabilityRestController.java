package com.springredis.controller;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.springredis.client.SpringRedisClient;
import com.springredis.pojo.Employee;

@EnableAutoConfiguration
@EnableWebMvc
@RestController
@ComponentScan(basePackages = "com.springredis")
@RequestMapping("/")
public class AvailabilityRestController {

	private static final Logger logger = LoggerFactory.getLogger(AvailabilityRestController.class);

	@Autowired
	private SpringRedisClient springRedisClient;

	@RequestMapping(value = "/availability/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> getAvailability(@PathVariable("id") String id,
			@RequestParam(name = "size", required = false) String size) {

		logger.debug("AvailabilityRestController :: getAvailability :: Entered ");
		Employee employee = springRedisClient.getAvailability(id, size);

		return Optional.ofNullable(employee).map(result -> new ResponseEntity<Employee>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(AvailabilityRestController.class);
		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		CountDownLatch latch = ctx.getBean(CountDownLatch.class);

		logger.info("Sending message...");
		template.convertAndSend("chat", "Hello from Redis!");

		latch.await();

		// System.exit(0);
	}

}
