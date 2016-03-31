package com.oracle.aone.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier(value = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createUser(String username, String email, String hash, String gravatar) {
		String sql = "INSERT INTO users (user_name, user_password, user_email, user_gravatar) VALUES (?,?,?,?)";
		jdbcTemplate.update(sql, username, hash, email, gravatar);
	}

	public Map<String, Object> getUserByEmail(String email) {
		String sql = "SELECT * from users where user_email = ?";
		return jdbcTemplate.queryForMap(sql, email);
	}

	public void update(String email, String newHash) {
		String sql = "Update users set user_password = ? where user_email = ?";
		jdbcTemplate.update(sql, newHash, email);
	}

}
