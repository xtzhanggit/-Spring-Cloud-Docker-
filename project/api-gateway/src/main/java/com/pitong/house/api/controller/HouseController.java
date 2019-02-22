package com.pitong.house.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.pitong.house.api.common.CommonConstants;
import com.pitong.house.api.common.PageData;
import com.pitong.house.api.common.PageParams;
import com.pitong.house.api.common.ResultMsg;
import com.pitong.house.api.common.UserContext;
import com.pitong.house.api.model.Comment;
import com.pitong.house.api.model.House;
import com.pitong.house.api.model.User;
import com.pitong.house.api.service.AgencyService;
import com.pitong.house.api.service.CommentService;
import com.pitong.house.api.service.HouseService;

@Controller
public class HouseController {

	@Autowired
	private HouseService houseService;

	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "house/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
		PageData<House> ps = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
		List<House> rcHouses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", rcHouses);
		modelMap.put("vo", query);
		modelMap.put("ps", ps);
		return "/house/listing";
	}

	@RequestMapping(value = "house/detail", method = { RequestMethod.POST, RequestMethod.GET })
	public String houseDetail(long id, ModelMap modelMap) {
		House house = houseService.queryOneHouse(id);
		List<Comment> comments = commentService.getHouseComments(id);
		List<House> rcHouses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
		if (house.getUserId() != null) {
			if (!Objects.equal(0L, house.getUserId())) {
				modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
			}
		}
		modelMap.put("house", house);
		modelMap.put("recomHouses", rcHouses);
		modelMap.put("commentList", comments);
		return "/house/detail";
	}

	@RequestMapping(value = "house/bookmark", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResultMsg bookmark(Long id, ModelMap modelMap) {
		User user = UserContext.getUser();
		houseService.bindUser2House(id, user.getId(), true);
		return ResultMsg.success();
	}

	@RequestMapping(value = "house/unbookmark", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResultMsg unbookmark(Long id, ModelMap modelMap) {
		User user = UserContext.getUser();
		houseService.unbindUser2House(id, user.getId(), true);
		return ResultMsg.success();
	}

}
