package com.pitong.house.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pitong.house.api.common.PageData;
import com.pitong.house.api.common.PageParams;
import com.pitong.house.api.dao.HouseDao;
import com.pitong.house.api.model.House;
import com.pitong.house.api.model.ListResponse;

@Service
public class HouseService {

	@Autowired
	private HouseDao houseDao;

	public PageData<House> queryHouse(House query, PageParams build) {
		ListResponse<House> result = houseDao.getHouses(query, build.getLimit(), build.getOffset());
		return PageData.<House>buildPage(result.getList(), result.getCount(), build.getPageSize(), build.getPageNum());
	}

	public List<House> getHotHouse(Integer recomSize) {
		List<House> list = houseDao.getHotHouse(recomSize);
		return list;
	}

	public House queryOneHouse(long id) {
		return houseDao.getOneHouse(id);
	}

	public void bindUser2House(Long houseId, Long userId, boolean bookmark) {
		houseDao.bindUser2House(houseId, userId, bookmark);
	}

	public void unbindUser2House(Long houseId, Long userId, boolean bookmark) {
		houseDao.unbindUser2House(houseId, userId, bookmark);
	}

}
