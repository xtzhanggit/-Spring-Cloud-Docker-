package com.pitong.house.api.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.pitong.house.api.dao.UserDao;
import com.pitong.house.api.model.User;
import com.pitong.house.api.utils.BeanHelper;

@Service
public class AccountService {

	@Value("${domain.name}")
	private String domainName;

	@Autowired
	private UserDao userDao;

	@Autowired
	private FileService fileService;

	public boolean isExit(String email) {
		return getUser(email) != null;
	}

	public User getUser(String email) {
		User queryUser = new User();
		queryUser.setEmail(email);
		List<User> users = getUserByQuery(queryUser);
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	public List<User> getUserByQuery(User query) {
		List<User> users = userDao.getUserList(query);
		return users;
	}

	public boolean addAccount(User account) {
		if (account.getAvatarFile() != null) {
			List<String> imgs = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
			account.setAvatar(imgs.get(0));
		}
		account.setEnableUrl("http://" + domainName + "/accounts/verify");
		BeanHelper.setDefaultProp(account, User.class);
		userDao.addUser(account);
		return true;
	}

	public boolean enable(String key) {
		return userDao.enable(key);
	}

	public User auth(String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return null;
		}
		User user = new User();
		user.setEmail(username);
		user.setPasswd(password);
		try {
			user = userDao.authUser(user);
		} catch (Exception e) {
			return null;
		}
		return user;
	}

	public void logout(String token) {
		userDao.logout(token);
	}

}
