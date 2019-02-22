package com.pitong.house.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pitong.house.api.common.RestResponse;
import com.pitong.house.api.config.GenericRest;
import com.pitong.house.api.model.User;

@Repository
@DefaultProperties(groupKey = "userDao", threadPoolKey = "userDao", commandProperties = {
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") }, threadPoolProperties = {
				@HystrixProperty(name = "coreSize", value = "10"),
				@HystrixProperty(name = "maxQueueSize", value = "1000") })
public class UserDao {

	@Autowired
	private GenericRest rest;

	@Value("${user.service.name}")
	private String userServiceName;

	@HystrixCommand
	public String getusername(Long id) {
		String url = "http://user/user/getusername?id=" + id;
		RestResponse<String> response = rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {
		}).getBody();
		return response.getResult();
	}
	
	@HystrixCommand
	public List<User> getUserList(User query) {
		String url = "http://" + userServiceName + "/user/getByList";
		ResponseEntity<RestResponse<List<User>>> resultEntity = rest.post(url, query,
				new ParameterizedTypeReference<RestResponse<List<User>>>() {
				});
		RestResponse<List<User>> response = resultEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		}
		return null;
	}

	@HystrixCommand
	public User addUser(User account) {
		String url = "http://" + userServiceName + "/user/add";
		ResponseEntity<RestResponse<User>> resultEntity = rest.post(url, account,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = resultEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		} else {
			throw new IllegalStateException("Can not add user");
		}
	}

	@HystrixCommand
	public boolean enable(String key) {
		String url = "http://" + userServiceName + "/user/enable?key=" + key;
		ResponseEntity<RestResponse<Object>> responseEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<Object>>() {
				});
		RestResponse<Object> response = responseEntity.getBody();
		if (response.getCode() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@HystrixCommand
	public User authUser(User user) {
		String url = "http://" + userServiceName + "/user/auth";
		ResponseEntity<RestResponse<User>> responseEntity = rest.post(url, user,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = responseEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		}
		{
			throw new IllegalStateException("Can not add user");
		}
	}
	

	@HystrixCommand()
	public void logout(String token) {
		String url = "http://" + userServiceName + "/user/logout?token=" + token;
		rest.get(url, new ParameterizedTypeReference<RestResponse<Object>>() {
		});
	}
	
	public User getUserByTokenFb(String token) {
		return new User();
	}

	@HystrixCommand(fallbackMethod = "getUserByTokenFb")
	public User getUserByToken(String token) {
		String url = "http://" + userServiceName + "/user/get?token=" + token;
		ResponseEntity<RestResponse<User>> responseEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = responseEntity.getBody();
		if (response == null || response.getCode() != 0) {
			return null;
		}
		return response.getResult();
	}

	@HystrixCommand
	public User getAgentById(Long id) {
		String url = "http://" + userServiceName + "/agency/agentDetail?id=" + id;
		ResponseEntity<RestResponse<User>> responseEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = responseEntity.getBody();
		if (response == null || response.getCode() != 0) {
			return null;
		}
		return response.getResult();
	}

}
