package com.oracle.aone.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.aone.service.CommentService;
import com.oracle.aone.util.BigDecimalUtil;

@Controller
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Resource
	private CommentService service;

	@RequestMapping(value = "/comment/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getComments(@PathVariable(value = "itemId") Integer itemIdInt) {
		BigDecimal itemId = BigDecimalUtil.parse(itemIdInt);
		logger.info("Getting comments for: " + itemId);
		return service.getCommentsForItem(itemId);
	}

	@RequestMapping(value = "/newComment", method = RequestMethod.POST)
	@ResponseBody
	public Boolean createComment(@RequestBody Map<String, Object> comment) {
		BigDecimal itemId = BigDecimalUtil.parse(comment.get("p1"));
		BigDecimal postedBy = BigDecimalUtil.parse(comment.get("p2"));
		String text = (String) comment.get("p3");

		logger.info("Creating Comment: " + itemId + ", " + postedBy + ", " + text);
		return service.createComment(itemId, postedBy, text);
	}
}
