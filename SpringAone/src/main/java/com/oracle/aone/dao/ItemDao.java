package com.oracle.aone.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier(value = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> getItems() {
		String sql = "select ITEMS.item_ID, ITEMS.item_TITLE, ITEMS.item_DESC,ITEMS.item_POST_DATE, "
				+ "ITEMS.item_posted_by,ITEMS.item_bought_by,USERS.USER_GRAVATAR,ITEMS.item_bought_by,ITEMS.item_PRICE, "
				+ "ITEMS.item_STATUS from ITEMS, USERS where ITEMS.item_posted_by = USERS.USER_ID";
		return jdbcTemplate.queryForList(sql);
	}

	public Boolean create(String title, String description, BigDecimal postedBy, String status, BigDecimal price) {
		String sql = "INSERT INTO items (item_title,item_desc,item_posted_by,item_status,item_price) "
				+ "values (?,?,?,?,?)";
		return jdbcTemplate.update(sql, title, description, postedBy, status, price) > 0;
	}

	public Map<String, Object> getItemByName(String title, BigDecimal postedBy) {
		String sql = "select ITEMS.item_ID, ITEMS.item_TITLE, ITEMS.item_DESC,ITEMS.item_POST_DATE, "
				+ "ITEMS.item_posted_by,ITEMS.item_bought_by,USERS.USER_GRAVATAR,ITEMS.item_PRICE, "
				+ "ITEMS.item_STATUS from ITEMS, USERS where ITEMS.item_posted_by = USERS.USER_ID "
				+ "and items.item_title = ? and items.item_posted_by = ?";
		return jdbcTemplate.queryForMap(sql, title, postedBy);
	}

	public Map<String, Object> getItembyId(BigDecimal id) {
		String sql = "select ITEMS.item_ID, ITEMS.item_TITLE, ITEMS.item_DESC,ITEMS.item_POST_DATE, "
				+ "ITEMS.item_posted_by,ITEMS.item_bought_by, USERS.USER_NAME, USERS.USER_GRAVATAR,ITEMS.item_PRICE, "
				+ "ITEMS.item_STATUS from ITEMS, USERS where ITEMS.item_posted_by = USERS.USER_ID and " + "item_id =?";
		return jdbcTemplate.queryForMap(sql, id);
	}

	public Boolean deleteItem(BigDecimal id) {
		String sql = "DELETE from items WHERE item_id = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	public Boolean updateItem(BigDecimal itemId, String title, String description, BigDecimal purchasedBy,
			BigDecimal price, String status) {
		String sql = "UPDATE items SET item_title = ?, item_desc = ?, item_bought_by = ?, item_price = ?,"
				+ " item_status = ? WHERE item_id = ?";
		return jdbcTemplate.update(sql, title, description, purchasedBy, price, status, itemId) > 0;
	}
}
