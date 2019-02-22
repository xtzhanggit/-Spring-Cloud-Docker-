package com.pitong.house.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pitong.house.api.model.User;
import com.pitong.house.api.service.AgencyService;

@Controller
public class AgencyController {

	@Autowired
	private AgencyService agencyService;

	@RequestMapping("agency/agentDetail")
	public String agentDetail(Long id, ModelMap modelMap) {
		User user = agencyService.getAgentDetail(id);
		modelMap.put("agent", user);
		return "/user/agent/agentDetail";
	}

}
