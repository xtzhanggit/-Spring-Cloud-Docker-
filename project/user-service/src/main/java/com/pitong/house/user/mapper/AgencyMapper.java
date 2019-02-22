package com.pitong.house.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pitong.house.user.common.PageParams;
import com.pitong.house.user.model.Agency;
import com.pitong.house.user.model.User;

@Mapper
public interface AgencyMapper {
	List<Agency> select(Agency agency);

	int insert(Agency agency);

	List<User> selectAgent(@Param("user") User user, @Param("pageParams") PageParams pageParams);

	Long selectAgentCount(@Param("user") User user);
}
