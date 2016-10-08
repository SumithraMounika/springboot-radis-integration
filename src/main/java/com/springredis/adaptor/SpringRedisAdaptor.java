package com.springredis.adaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springredis.factory.SpringRedisFactory;
import com.springredis.pojo.Employee;

@Service
public class SpringRedisAdaptor {

	@Autowired
	private SpringRedisFactory springRedisFactory;
	
	
	public Employee getAvailability(String id,String size) {
		Employee employee = springRedisFactory.getAvailability(id,size);

		return employee;
	}

}
