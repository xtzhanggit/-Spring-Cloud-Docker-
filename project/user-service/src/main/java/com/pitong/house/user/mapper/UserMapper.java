package com.pitong.house.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pitong.house.user.model.User;

@Mapper
public interface UserMapper {

	User selectById(Long id);
	List<User> select(User user);
	int update(User account); // 返回更新的数据库行数
	int delete(String email);
	int insert(User user);
	User selectByEmail(String email);
}
