package com.oracle.aone.dao;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
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
public class CommentDaoTest {
	@Resource
	private CommentDao dao;

	@Test
	public void createAndGetComments() throws Exception {
		// setup
		String comment1 = "Hi";
		String comment2 = "Bye";
		BigDecimal itemId = BigDecimal.ONE;
		BigDecimal postedBy = BigDecimal.TEN;

		// execute
		dao.createComment(itemId, postedBy, comment1);
		dao.createComment(itemId, postedBy, comment2);
		List<Map<String, Object>> comments = dao.getCommentsForItem(itemId);

		// assert
		assertTrue(comments.size() == 2);
	}
}
