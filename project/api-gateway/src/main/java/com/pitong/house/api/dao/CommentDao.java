package com.pitong.house.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.pitong.house.api.common.RestResponse;
import com.pitong.house.api.config.GenericRest;
import com.pitong.house.api.model.Blog;
import com.pitong.house.api.model.BlogQueryReq;
import com.pitong.house.api.model.Comment;
import com.pitong.house.api.model.CommentReq;
import com.pitong.house.api.model.ListResponse;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

@Repository
public class CommentDao {

	@Value("${comment.service.name}")
	private String commentServiceName;

	@Autowired
	private GenericRest rest;

	public List<Comment> listComment(CommentReq commentReq) {
		String url = "http://" + commentServiceName + "/comment/list";
		ResponseEntity<RestResponse<List<Comment>>> resultEntity = rest.post(url, commentReq,
				new ParameterizedTypeReference<RestResponse<List<Comment>>>() {
				});
		RestResponse<List<Comment>> response = resultEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		}
		return null;
	}

	public Pair<List<Blog>, Long> getBlogs(Blog query, Integer limit, Integer offset) {
		String url = "http://" + commentServiceName + "/blog/list";
		BlogQueryReq blogQueryReq = new BlogQueryReq();
		blogQueryReq.setBlog(query);
		blogQueryReq.setLimit(limit);
		blogQueryReq.setOffset(offset);
		ResponseEntity<RestResponse<ListResponse<Blog>>> resultEntity = rest.post(url, blogQueryReq,
				new ParameterizedTypeReference<RestResponse<ListResponse<Blog>>>() {
				});
		RestResponse<ListResponse<Blog>> response = resultEntity.getBody();
		ListResponse<Blog> listResponse = null;
		if (response.getCode() == 0) {
			listResponse = response.getResult();
		}
		return ImmutablePair.of(listResponse.getList(), listResponse.getCount());
	}

	public Blog getBlog(int id) {
		String url = "http://" + commentServiceName + "/blog/one?id=" + id;
		ResponseEntity<RestResponse<Blog>> resultEntity = rest.get(url,
				new ParameterizedTypeReference<RestResponse<Blog>>() {
				});
		RestResponse<Blog> response = resultEntity.getBody();
		if (response.getCode() == 0) {
			return response.getResult();
		}
		return null;
	}

}
