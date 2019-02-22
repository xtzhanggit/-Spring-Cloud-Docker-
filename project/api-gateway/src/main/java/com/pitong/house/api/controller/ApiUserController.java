package com.pitong.house.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pitong.house.api.common.RestResponse;
import com.pitong.house.api.service.UserService;

@RestController
public class ApiUserController {

	@Autowired
	private UserService userService;

	@RequestMapping("test/getusername")
	public RestResponse<String> getusername(Long id) {
		return RestResponse.success(userService.getusername(id));
	}

}
