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
public class CommentDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier(value = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> getCommentsForItem(BigDecimal itemId) {
		String sql = "SELECT USERS.USER_NAME, USERS.USER_GRAVATAR, COMMENTS.COMMENT_ID, COMMENTS.COMMENT_BY, COMMENTS.COMMENT_TEXT, "
				+ "COMMENTS.COMMENT_CREATE_DATE from USERS, COMMENTS where COMMENTS.COMMENT_BY = USERS.user_id and COMMENTS.item_id =?";
		return jdbcTemplate.queryForList(sql, itemId);
	}

	public Boolean createComment(BigDecimal itemId, BigDecimal postedBy, String text) {
		String sql = "INSERT INTO comments (item_id,comment_by,comment_text) VALUES (?,?,?)";
		return jdbcTemplate.update(sql, itemId, postedBy, text) > 0;
	}

}
