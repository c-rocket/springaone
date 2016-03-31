package com.oracle.aone.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.oracle.aone.dao.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserDao dao;

	@InjectMocks
	private UserService service;

	@Test
	public void changePassword_validExisting() throws Exception {
		// setup
		StandardPasswordEncoder encoder = new StandardPasswordEncoder(UserService.KEY);

		String email = "Test.user@oracle.com";
		String oldpw = "hi";
		String newpw = "bye";

		String oldHash = encoder.encode(oldpw);

		HashMap<String, Object> user = new HashMap<String, Object>();
		user.put("user_password", oldHash);
		Mockito.when(dao.getUserByEmail(email)).thenReturn(user);

		// execute
		service.changePassword(email, oldpw, newpw);

		// assert
		Mockito.verify(dao, Mockito.times(1)).update(Mockito.eq(email), Mockito.anyString());
	}

	@Test
	public void changePassword_invalidExisting() throws Exception {
		// setup
		StandardPasswordEncoder encoder = new StandardPasswordEncoder(UserService.KEY);
		String email = "Test.user@oracle.com";
		String oldpw = "hi";
		String newpw = "bye";

		String hash = encoder.encode("some weird hash");

		HashMap<String, Object> user = new HashMap<String, Object>();
		user.put("user_password", hash);
		Mockito.when(dao.getUserByEmail(email)).thenReturn(user);

		// execute
		service.changePassword(email, oldpw, newpw);

		// assert
		Mockito.verify(dao, Mockito.never()).update(Mockito.eq(email), Mockito.anyString());
	}

	@Test
	public void login_validExisting() throws Exception {
		// setup
		StandardPasswordEncoder encoder = new StandardPasswordEncoder(UserService.KEY);

		String email = "Test.user@oracle.com";
		String password = "hi";

		String hash = encoder.encode(password);

		HashMap<String, Object> user = new HashMap<String, Object>();
		user.put("user_password", hash);
		Mockito.when(dao.getUserByEmail(email)).thenReturn(user);

		// execute
		Map<String, Object> loggedInUser = service.login(email, password);

		// assert
		assertNotNull(loggedInUser);
	}

	@Test
	public void login_invalidExisting() throws Exception {
		// setup
		StandardPasswordEncoder encoder = new StandardPasswordEncoder(UserService.KEY);
		String email = "Test.user@oracle.com";
		String password = "hi";

		String hash = encoder.encode("some weird hash");

		HashMap<String, Object> user = new HashMap<String, Object>();
		user.put("user_password", hash);
		Mockito.when(dao.getUserByEmail(email)).thenReturn(user);

		// execute
		Map<String, Object> loggedInUser = service.login(email, password);

		// assert
		assertNull(loggedInUser);
	}
}
