package com.springredis.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;

import com.springredis.pojo.Employee;

@Repository
public class SpringRedisFactory {

	private static final Logger logger = LoggerFactory.getLogger(SpringRedisFactory.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public Employee getAvailability(String id, String size) {
		saveAvailability();
		ValueOperations<String, String> opsValue = stringRedisTemplate.opsForValue();
		final String name = opsValue.get(id);
		logger.debug("Ops for value : "+name);
		if (name != null) {
			throw new ResourceAccessException("There are no value present for the given id " + id);
		}
		Employee employee = new Employee();
		employee.setName(name);

		return employee;
	}

	private void saveAvailability() {
		Employee employee = new Employee();
		employee.setId("101");
		employee.setAge(29);
		employee.setName("kumar");
		ValueOperations<String, String> opsValue = stringRedisTemplate.opsForValue();
		opsValue.set(employee.getId(), employee.getName());

		logger.debug("keys in redis ");
		stringRedisTemplate.keys("*").forEach(key -> logger.debug(key));

	}

}
