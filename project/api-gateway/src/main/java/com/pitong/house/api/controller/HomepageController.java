package com.pitong.house.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pitong.house.api.dao.UserDao;

@Controller
public class HomepageController {

	@RequestMapping("index")
	public String accountsRegister(ModelMap modelMap) {
		return "/homepage/index";
	}

	@RequestMapping("")
	public String index(ModelMap modelMap) {
		return "redirect:/index";
	}
}
