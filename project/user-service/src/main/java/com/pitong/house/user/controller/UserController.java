package com.pitong.house.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pitong.house.user.common.RestResponse;

import com.pitong.house.user.model.User;
import com.pitong.house.user.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/*----------------------------用户服务的查询接口--------------------------------*/

	@RequestMapping("getById") // 通过用户id查询
	public RestResponse<User> getUserById(Long id) {
		User user = userService.getUserById(id);
		return RestResponse.success(user);
	}

	@RequestMapping("getByList") // 通过用户属性list查询
	public RestResponse<List<User>> getUserByList(@RequestBody User user) { // 接收post请求，接收json对象，并反序列化为User对象
		List<User> users = userService.getUserByQuery(user);
		return RestResponse.success(users);
	}

	/*----------------------------用戶服务的注册接口--------------------------------*/

	@RequestMapping("add") // 接受用户的注册信息
	public RestResponse<User> add(@RequestBody User user) {
		userService.addAccount(user, user.getEnableUrl()); // 添加新用户至数据库并发送激活链接，无返回值
		return RestResponse.success(); // 直接返回result为null的OK响应
	}

	@RequestMapping("enable") // 用户注册的验证接口
	public RestResponse<User> enable(String key) {
		userService.enable(key);
		return RestResponse.success();
	}

	/*----------------------------用戶服务的登陆/鉴权接口--------------------------------*/

	@RequestMapping("auth") // 用户登陆接口
	public RestResponse<User> auth(@RequestBody User user) {
		User finalUser = userService.auth(user.getEmail(), user.getPasswd());
		return RestResponse.success(finalUser);
	}

	@RequestMapping("get") // 用户鉴权接口
	public RestResponse<User> getUser(String token) {
		User finalUser = userService.getLoginedUserByToken(token);
		return RestResponse.success(finalUser);
	}

	@RequestMapping("logout") // 用户登出接口
	public RestResponse<User> logout(String token) {
		userService.invalidate(token);
		return RestResponse.success();
	}

	/*----------------------------用戶服务的更新接口--------------------------------*/

	@RequestMapping("update")
	public RestResponse<User> update(@RequestBody User user) {
		User updateUser = userService.updateUser(user);
		return RestResponse.success(updateUser);
	}

	@Value("${server.port}")
	private Integer port;

	@RequestMapping("getusername")
	public RestResponse<String> getUsername(Long id) {
		LOGGER.info("Request coming, and my server port is" + port);
		return RestResponse.success("test-name" + port);
	}

}
