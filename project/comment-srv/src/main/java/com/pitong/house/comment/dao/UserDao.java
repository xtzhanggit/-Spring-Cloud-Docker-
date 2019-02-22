package com.pitong.house.comment.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.pitong.house.comment.model.User;
import com.pitong.house.comment.common.RestResponse;
import com.pitong.house.comment.config.GenericRest;

@Repository
public class UserDao {

	@Autowired
	private GenericRest rest;

	@Value("${user.service.name}")
	private String userServiceName;

	public User getUserDetail(Long userId) {
		String url = "http://" + userServiceName + "/user/getById?id=" + userId;
		ResponseEntity<RestResponse<User>> responseEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = responseEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		}
		return null;
	}

}
