package com.pitong.house.hsrv.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.pitong.house.hsrv.common.RestResponse;
import com.pitong.house.hsrv.config.GenericRest;
import com.pitong.house.hsrv.model.User;

@Repository
public class UserDao {

	@Autowired
	private GenericRest rest;

	@Value("${user.service.name}")
	private String userServiceName;

	public User getAgentDetail(Long agentId) {
		String url = "http://" + userServiceName + "/agency/agentDetail" + "?id=" + agentId;
		ResponseEntity<RestResponse<User>> responseEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<User>>() {
				});
		RestResponse<User> response = responseEntity.getBody();
		if(response.getCode() == 0) {
			return response.getResult();
		}
		return null;
	}

}
