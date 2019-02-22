package com.pitong.house.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.pitong.house.api.common.HouseUserType;
import com.pitong.house.api.common.RestResponse;
import com.pitong.house.api.config.GenericRest;
import com.pitong.house.api.model.House;
import com.pitong.house.api.model.HouseQueryReq;
import com.pitong.house.api.model.HouseUserReq;
import com.pitong.house.api.model.ListResponse;
import com.pitong.house.api.model.User;

@Repository
public class HouseDao {

	@Value("${house.service.name}")
	private String houseServiceName;

	@Autowired
	private GenericRest rest;

	public ListResponse<House> getHouses(House query, Integer limit, Integer offset) {
		String url = "http://" + houseServiceName + "/house/list";
		HouseQueryReq req = new HouseQueryReq();
		req.setLimit(limit);
		req.setOffset(offset);
		req.setQuery(query);
		ResponseEntity<RestResponse<ListResponse<House>>> resultEntity = rest.post(url, req,
				new ParameterizedTypeReference<RestResponse<ListResponse<House>>>() {
				});
		RestResponse<ListResponse<House>> response = resultEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		}
		return null;
	}

	public List<House> getHotHouse(Integer recomSize) {
		String url = "http://" + houseServiceName + "/house/hot?size=" + recomSize;
		ResponseEntity<RestResponse<List<House>>> responseEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<List<House>>>() {
				});
		RestResponse<List<House>> response = responseEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		}
		return null;
	}

	public House getOneHouse(long id) {
		String url = "http://" + houseServiceName + "/house/detail?id=" + id;
		ResponseEntity<RestResponse<House>> responseEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<House>>() {
				});
		RestResponse<House> response = responseEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		}
		return null;
	}

	public void bindUser2House(Long houseId, Long userId, boolean bookmark) {
		HouseUserReq req = new HouseUserReq();
		req.setUnBind(false);
		req.setBindType(HouseUserType.BOOKMARK.value);
		req.setUserId(userId);
		req.setHouseId(houseId);
		bindOrInBind(req);
	}

	private void bindOrInBind(HouseUserReq req) {
		String url = "http://" + houseServiceName + "/house/bind";
		ResponseEntity<RestResponse<Object>> resultEntity = rest.post(url, req,
				new ParameterizedTypeReference<RestResponse<Object>>() {
				});
		RestResponse<Object> response = resultEntity.getBody();
	}

	public void unbindUser2House(Long houseId, Long userId, boolean bookmark) {
		HouseUserReq req = new HouseUserReq();
		req.setUnBind(true);
		if(bookmark) {
			req.setBindType(HouseUserType.BOOKMARK.value);
		} else {
			req.setBindType(HouseUserType.SALE.value);
		}
		req.setUserId(userId);
		req.setHouseId(houseId);
		bindOrInBind(req);
	}

}
