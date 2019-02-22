package com.pitong.house.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pitong.house.api.dao.UserDao;
import com.pitong.house.api.model.User;

@Service
public class AgencyService {

	@Autowired
	private UserDao userDao;

	@Value("${file.prefix}")
	private String prefix;

	public User getAgentDetail(Long id) {
		return userDao.getAgentById(id);
	}

}
