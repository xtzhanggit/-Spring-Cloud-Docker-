package com.pitong.house.comment.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.pitong.house.comment.dao.UserDao;
import com.pitong.house.comment.mapper.CommentMapper;
import com.pitong.house.comment.model.Comment;
import com.pitong.house.comment.model.User;

@Service
public class CommentService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private StringRedisTemplate redisTemplate;

	public List<Comment> getHouseComments(Long houseId, Integer size) {
		String key = "house_comments" + "_" + houseId + "_" + size;
		String json = redisTemplate.opsForValue().get(key);
		List<Comment> lists = null;
		if (Strings.isNullOrEmpty(json)) {
			lists = doGetHouseComments(houseId, size);
			redisTemplate.opsForValue().set(key, JSON.toJSONString(lists));
			redisTemplate.expire(key, 5, TimeUnit.MINUTES);
		} else {
			lists = JSON.parseObject(json, new TypeReference<List<Comment>>() {
			});
		}
		return lists;
	}

	public List<Comment> doGetHouseComments(Long houseId, Integer size) {
		List<Comment> comments = commentMapper.selectComments(houseId, size);
		comments.forEach(comment -> {
			User user = userDao.getUserDetail(comment.getUserId());
			comment.setUserName(user.getName());
			comment.setAvatar(user.getAvatar());
		});
		return comments;
	}

	public List<Comment> getBlogComments(Integer blogId, Integer size) {
		String key = "blog_comments" + "_" + blogId + "_" + size;
		String json = redisTemplate.opsForValue().get(key);
		List<Comment> lists = null;
		if (Strings.isNullOrEmpty(json)) {
			lists = doGetBlogComments(blogId, size);
			redisTemplate.opsForValue().set(key, JSON.toJSONString(lists));
			redisTemplate.expire(key, 5, TimeUnit.MINUTES);
		} else {
			lists = JSON.parseObject(json, new TypeReference<List<Comment>>() {
			});
		}
		return lists;
	}

	public List<Comment> doGetBlogComments(Integer blogId, Integer size) {
		List<Comment> comments = commentMapper.selectBlogComments(blogId, size);
		comments.forEach(comment -> {
			User user = userDao.getUserDetail(comment.getUserId());
			comment.setAvatar(user.getAvatar());
			comment.setUserName(user.getName());
		});
		return comments;
	}

}
