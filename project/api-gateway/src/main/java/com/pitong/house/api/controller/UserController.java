package com.pitong.house.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pitong.house.api.common.ResultMsg;
import com.pitong.house.api.common.UserContext;
import com.pitong.house.api.model.User;
import com.pitong.house.api.service.AccountService;
import com.pitong.house.api.utils.UserHelper;

@Controller
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "accounts/register", method = { RequestMethod.POST, RequestMethod.GET })
	public String accountsSubmit(User account, ModelMap modelMap) {
		// 初次访问返回注册页面
		if (account == null || account.getName() == null) {
			return "/user/accounts/register";
		}
		ResultMsg resultMsg = UserHelper.validate(account);
		if (resultMsg.isSuccess()) {
			boolean exit = accountService.isExit(account.getEmail());
			if (!exit) {
				accountService.addAccount(account);
				modelMap.put("email", account.getEmail());
				return "/user/accounts/registerSubmit";
			} else {
				return "redirect:/accounts/register?" + ResultMsg.errorMsg("邮箱已被占用").asUrlParams();
			}
		} else {
			return "redirect:/accounts/register?" + ResultMsg.errorMsg("参数错误").asUrlParams();
		}
	}

	@RequestMapping("accounts/verify")
	public String verify(String key) {
		boolean result = accountService.enable(key);
		if (result) {
			return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
		} else {
			return "redirect:/index?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期").asUrlParams();
		}
	}

	@RequestMapping(value = "/accounts/signin", method = { RequestMethod.POST, RequestMethod.GET })
	public String loginSubmit(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if (username == null || password == null) {
			req.setAttribute("target", req.getParameter("target"));
			return "/user/accounts/signin";
		}
		User user = accountService.auth(username, password);
		if (user == null) {
			return "redirect:/accounts/signin?" + "username=" + username + "&"
					+ ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
		} else {
			UserContext.setUser(user);
			return StringUtils.isNotBlank(req.getParameter("target")) ? "redirect:" + req.getParameter("target")
					: "redirect:/index";
		}
	}

	@RequestMapping("accounts/logout")
	public String logout(HttpServletRequest req) {
		User user = UserContext.getUser();
		accountService.logout(user.getToken());
		return "redirect:/index";
	}

}
