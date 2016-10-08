package com.springredis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springredis.adaptor.SpringRedisAdaptor;
import com.springredis.pojo.Employee;

@Service
public class SpringRedisClient {

	@Autowired
	private SpringRedisAdaptor springRedisAdaptor;

	public Employee getAvailability(String id, String size) {

		Employee employee = springRedisAdaptor.getAvailability(id, size);

		return employee;
	}
}
