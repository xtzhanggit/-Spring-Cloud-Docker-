package com.pitong.house.user.controller;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pitong.house.user.common.PageParams;
import com.pitong.house.user.common.RestResponse;
import com.pitong.house.user.model.ListResponse;
import com.pitong.house.user.model.User;
import com.pitong.house.user.service.AgencyService;

@RestController
@RequestMapping("agency")
public class AgencyController {

	@Autowired
	private AgencyService agencyService;

	@RequestMapping("agentList")
	public RestResponse<ListResponse<User>> agentList(Integer limit, Integer offset) {
		PageParams pageParams = new PageParams();
		pageParams.setLimit(limit);
		pageParams.setOffset(offset);
		Pair<List<User>, Long> pair = agencyService.getAllAgent(pageParams);
		ListResponse<User> response = ListResponse.build(pair.getKey(), pair.getValue());
		return RestResponse.success(response);
	}
	
	@RequestMapping("agentDetail")
	public RestResponse<User> agentDetail(Long id) {
		User user = agencyService.getAgentDetail(id);
		return RestResponse.success(user);
	}

}
