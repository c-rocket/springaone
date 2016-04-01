package com.oracle.aone.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AOneDataSource{
	
	public void createUser(String username, String email, String hash, String gravatar) {
		String sql = "INSERT INTO users (user_name, user_password, user_email, user_gravatar) VALUES (?,?,?,?)";
		getJdbcTemplate().update(sql, username, hash, email, gravatar);
	}

	public Map<String, Object> getUserByEmail(String email) {
		String sql = "SELECT * from users where user_email = ?";
		return getJdbcTemplate().queryForMap(sql, email);
	}

	public void update(String email, String newHash) {
		String sql = "Update users set user_password = ? where user_email = ?";
		getJdbcTemplate().update(sql, newHash, email);
	}

}
