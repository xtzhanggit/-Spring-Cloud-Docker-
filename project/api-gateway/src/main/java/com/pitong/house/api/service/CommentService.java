package com.pitong.house.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pitong.house.api.common.PageData;
import com.pitong.house.api.common.PageParams;
import com.pitong.house.api.dao.CommentDao;
import com.pitong.house.api.model.Blog;
import com.pitong.house.api.model.Comment;
import com.pitong.house.api.model.CommentReq;
import org.apache.commons.lang3.tuple.Pair;

@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;

	public List<Comment> getHouseComments(long id) {
		CommentReq commentReq = new CommentReq();
		commentReq.setHouseId(id);
		commentReq.setType(1);
		commentReq.setSize(8);
		return commentDao.listComment(commentReq);
	}

	public PageData<Blog> queryBlogs(Blog query, PageParams build) {
		Pair<List<Blog>, Long> pair = commentDao.getBlogs(query, build.getLimit(), build.getOffset());
		return PageData.buildPage(pair.getKey(), pair.getValue(), build.getPageSize(), build.getPageNum());
	}

	public Blog queryOneBlog(int id) {
		return commentDao.getBlog(id);
	}

	public List<Comment> getBlogComments(int id) {
		CommentReq commentReq = new CommentReq();
		commentReq.setBlogId(id);
		commentReq.setType(2);
		commentReq.setSize(8);
		return commentDao.listComment(commentReq);
	}

}
