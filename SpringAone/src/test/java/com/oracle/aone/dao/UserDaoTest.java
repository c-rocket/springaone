package com.oracle.aone.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/test-context.xml" })
@Transactional
public class UserDaoTest {

	@Resource
	private UserDao dao;

	@Test
	public void createAndGetUser() throws Exception {
		// setup
		String username = "test uesr";
		String email = "test.user@oracle.com";
		String password = "password";
		String gravatar = "gravatar";

		// execute
		dao.createUser(username, email, password, gravatar);
		Map<String, Object> user = dao.getUserByEmail(email);

		// assert
		assertNotNull(user);
		assertEquals((String) user.get("user_name"), username);
		assertEquals((String) user.get("user_password"), password);
		assertEquals((String) user.get("user_email"), email);
		assertEquals((String) user.get("user_gravatar"), gravatar);
	}

	@Test
	public void updatePassword() throws Exception {
		// setup
		String username = "test uesr";
		String email = "test.user@oracle.com";
		String password = "password";
		String newPassword = "password2";
		String gravatar = "gravatar";

		// execute
		dao.createUser(username, email, password, gravatar);
		dao.update(email, newPassword);
		Map<String, Object> user = dao.getUserByEmail(email);

		// assert
		assertNotNull(user);
		assertEquals(user.get("user_name"), username);
		assertEquals(user.get("user_password"), newPassword);
		assertEquals(user.get("user_email"), email);
		assertEquals(user.get("user_gravatar"), gravatar);
	}

}
