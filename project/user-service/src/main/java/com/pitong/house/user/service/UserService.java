package com.pitong.house.user.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.pitong.house.user.exception.UserException;
import com.pitong.house.user.exception.UserException.Type;
import com.pitong.house.user.mapper.UserMapper;
import com.pitong.house.user.model.User;
import com.pitong.house.user.utils.BeanHelper;
import com.pitong.house.user.utils.HashUtils;
import com.pitong.house.user.utils.JwtHelper;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	/**
	 * 1.首先通过缓存获取 2.缓存不存在则从数据库获取，并将用户对象写入缓存，设置缓存时间5分钟 3.返回对象
	 */

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private UserMapper userMapper;

	@Value("{$file.prefix}")
	private String imgPrefix;

	//@Autowired
	//private MailService mailService;

	public User getUserById(Long id) {
		String key = "user:" + id;
		String json = redisTemplate.opsForValue().get(key); // 先从redis获取
		User user = null;
		if (Strings.isNullOrEmpty(json)) { // redis不包含所需对象
			user = userMapper.selectById(id);
			user.setAvatar(imgPrefix + user.getAvatar()); // 为user对象添加图片绝对路径前缀
			String string = JSON.toJSONString(user);
			redisTemplate.opsForValue().set(key, string);
			redisTemplate.expire(key, 5, TimeUnit.MINUTES);
		} else { // redis包含所需对象
			user = JSON.parseObject(json, User.class); // 将json字符串反序列化为User对象
		}
		return userMapper.selectById(id);
	}

	public List<User> getUserByQuery(User user) {
		List<User> users = userMapper.select(user);
		users.forEach(u -> {
			u.setAvatar(imgPrefix + u.getAvatar()); // 依次为所有user对象添加图片绝对路径前缀
		});
		return users;
	}

	public boolean addAccount(User user, String enableUrl) {
		user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
		BeanHelper.onInsert(user); // 设置创建时间
		userMapper.insert(user); // 将注册信息插入数据库
		registerNotify(user.getEmail(), enableUrl); // 发送注册邮件
		return true;
	}

	private void registerNotify(String email, String enableUrl) {
		String randomKey = HashUtils.hashString(email) + RandomStringUtils.randomAlphabetic(10); // 生成随机字段
		redisTemplate.opsForValue().set(randomKey, email); // 注入redis缓存
		redisTemplate.expire(randomKey, 1, TimeUnit.HOURS);
		String content = enableUrl + "?key=" + randomKey;
		//mailService.sendSimpleMail("房产平台激活邮件", content, email); // 发送邮件
		//LOGGER.info("已发送邮件至" + email);
	}

	public boolean enable(String key) {
		String email = redisTemplate.opsForValue().get(key);
		if (StringUtils.isBlank(email)) {
			throw new UserException(Type.USER_NOT_FOUND, "无效的key");
		}
		User updateUser = new User();
		updateUser.setEmail(email);
		updateUser.setEnable(1);
		userMapper.update(updateUser);
		return true;
	}

	public User auth(String email, String passwd) {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(email)) {
			throw new UserException(Type.USER_AUTH_FAIL, "User Auth Fail");
		}
		User user = new User();
		user.setEmail(email);
		user.setPasswd(HashUtils.encryPassword(passwd));
		user.setEnable(1);
		List<User> list = getUserByQuery(user);
		if (!list.isEmpty()) {
			User retUser = list.get(0);
			onLogin(retUser); // 加入token
			return retUser;
		}
		throw new UserException(Type.USER_AUTH_FAIL, "User Auth Fail");
	}

	private void onLogin(User user) {
		String token = JwtHelper.genToken(ImmutableMap.of("email", user.getEmail(), "name", user.getName(), "ts",
				Instant.now().getEpochSecond() + ""));
		renewToken(token, user.getEmail());
		user.setToken(token);
	}

	private String renewToken(String token, String email) {
		redisTemplate.opsForValue().set(email, token);
		redisTemplate.expire(email, 30, TimeUnit.MINUTES);
		return token;
	}

	public User getLoginedUserByToken(String token) {
		Map<String, String> map = null;
		try {
			map = JwtHelper.verifyToken(token);
		} catch (Exception e) {
			throw new UserException(Type.USER_NOT_LOGIN, "User Not Login");
		}
		String email = map.get("email");
		Long expired = redisTemplate.getExpire(email);
		if (expired > 0) {
			renewToken(token, email);
			User user = getUserByEmail(email);
			user.setToken(token);
			return user;
		}
		throw new UserException(Type.USER_NOT_LOGIN, "User Not Login");
	}

	private User getUserByEmail(String email) {
		User user = new User();
		user.setEmail(email);
		List<User> list = getUserByQuery(user);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		throw new UserException(Type.USER_NOT_FOUND, "User Not Found For " + email);
	}

	public void invalidate(String token) {
		Map<String, String> map = JwtHelper.verifyToken(token);
		redisTemplate.delete(map.get("email"));
	}

	@Transactional(rollbackFor = Exception.class) // 异常会数据回滚
	public User updateUser(User user) {
		if (user.getEmail() == null) {
			return null;
		}
		if (!Strings.isNullOrEmpty(user.getPasswd())) {
			user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
		}
		userMapper.update(user);
		return userMapper.selectByEmail(user.getEmail());
	}

}
