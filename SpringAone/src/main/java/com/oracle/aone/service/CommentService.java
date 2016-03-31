package com.oracle.aone.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.aone.dao.CommentDao;

@Service
@Transactional
public class CommentService {

	@Resource
	private CommentDao dao;

	public List<Map<String, Object>> getCommentsForItem(BigDecimal itemId) {
		return dao.getCommentsForItem(itemId);
	}

	public Boolean createComment(BigDecimal itemId, BigDecimal postedBy, String text) {
		return dao.createComment(itemId, postedBy, text);
	}

}
