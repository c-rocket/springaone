package com.oracle.aone.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.aone.dao.ItemDao;

@Service
@Transactional
public class ItemService {

	@Resource
	private ItemDao dao;

	public List<Map<String, Object>> getItems() {
		return dao.getItems();
	}

	public Map<String, Object> createItem(String title, String description, BigDecimal postedBy, String status,
			BigDecimal price) {
		dao.create(title, description, postedBy, status, price);
		return dao.getItemByName(title, postedBy);
	}

	public Map<String, Object> getItem(BigDecimal id) {
		return dao.getItembyId(id);
	}

	public Boolean deleteItem(BigDecimal id) {
		return dao.deleteItem(id);
	}

	public Boolean updateItem(BigDecimal itemId, String title, String description, BigDecimal purchasedBy,
			BigDecimal price, String status) {
		return dao.updateItem(itemId, title, description, purchasedBy, price, status);
	}

}
