package com.oracle.aone.service;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.aone.dao.UserDao;
import com.oracle.aone.util.Gravatar;

@Service
@Transactional
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Resource
	private UserDao dao;

	public static final String KEY = "SuperSecretKey";

	public Boolean changePassword(String email, String oldpw, String newpw) {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder(KEY);
		String newHash = encoder.encode(newpw);

		Map<String, Object> user = dao.getUserByEmail(email);
		try {
			if (encoder.matches(oldpw, (String) user.get("user_password"))) {
				dao.update(email, newHash);
				return true;
			}
		} catch (Exception e) {
			logger.error("Error matching", e);
		}
		return false;
	}

	public Map<String, Object> createUser(String username, String email, String password) {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder(KEY);
		String hash = encoder.encode(password);
		String gravatar = Gravatar.url(email);
		dao.createUser(username, email, hash, gravatar);
		return dao.getUserByEmail(email);
	}

	public Map<String, Object> login(String email, String password) {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder(KEY);
		Map<String, Object> user = dao.getUserByEmail(email);
		try {
			if (user != null && encoder.matches(password, (String) user.get("user_password"))) {
				return user;
			}
		} catch (Exception e) {
			logger.error("Error matching", e);
		}
		return null;
	}

	public void setDao(UserDao dao) {
		this.dao = dao;
	}

}
