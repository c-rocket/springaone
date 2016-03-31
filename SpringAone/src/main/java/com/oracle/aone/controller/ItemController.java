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

import com.oracle.aone.service.ItemService;
import com.oracle.aone.util.BigDecimalUtil;

@Controller
public class ItemController {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Resource
	private ItemService service;

	@RequestMapping(value = "/items", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getItems() {
		logger.info("Getting items");
		return service.getItems();
	}

	@RequestMapping(value = "/item", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getItem() {
		logger.info("Getting items");
		return service.getItems();
	}

	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getItem(@PathVariable(value = "itemId") BigDecimal id) {
		logger.info("Getting items");
		return service.getItem(id);
	}

	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean deleteItem(@PathVariable(value = "itemId") BigDecimal id) {
		logger.info("Getting items");
		return service.deleteItem(id);
	}

	@RequestMapping(value = "/item", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createItem(@RequestBody Map<String, Object> item) {
		String title = (String) item.get("p1");
		String description = (String) item.get("p2");
		BigDecimal postedBy = BigDecimalUtil.parse(item.get("p3"));
		String status = (String) item.get("p4");
		BigDecimal price = BigDecimalUtil.parse(item.get("p5"));
		logger.info("Creating Item: " + title + ", " + description + ", " + postedBy + ", " + status + ", " + price);
		return service.createItem(title, description, postedBy, status, price);
	}

	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.PUT)
	@ResponseBody
	public Boolean updateItem(@PathVariable(value = "itemId") Integer itemIdInt,
			@RequestBody Map<String, Object> item) {
		BigDecimal itemId = new BigDecimal(itemIdInt);
		String title = (String) item.get("p1");
		String description = (String) item.get("p2");
		BigDecimal purchasedBy = BigDecimalUtil.parse(item.get("p3"));
		BigDecimal price = BigDecimalUtil.parse(item.get("p4"));
		String status = (String) item.get("p5");
		logger.info("Updating Item: " + itemId + ", " + title + ", " + description + ", " + purchasedBy + ", " + status
				+ ", " + price);
		return service.updateItem(itemId, title, description, purchasedBy, price, status);
	}
}
