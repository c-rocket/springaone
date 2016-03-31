package com.oracle.aone.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/test-context.xml" })
@Transactional
public class ItemDaoTest {

	@Resource
	private ItemDao dao;

	@Test
	public void getItems() throws Exception {
		// execute
		List<Map<String, Object>> items = dao.getItems();

		// assert
		assertTrue(items.size() > 0);
	}

	@Test
	public void createAndGetItem() throws Exception {
		// setup
		String title = "test item in the house";
		String description = "about this test...s";
		BigDecimal postedBy = BigDecimal.ONE;
		String status = "open";
		BigDecimal price = BigDecimal.TEN;

		// execute
		Boolean created = dao.create(title, description, postedBy, status, price);
		Map<String, Object> testItem = dao.getItemByName(title, postedBy);
		Map<String, Object> itembyId = dao.getItembyId((BigDecimal) testItem.get("ITEM_ID"));

		// assert
		assertTrue(created);
		assertNotNull(testItem);
		assertItem(testItem, title, description, postedBy, status, price);
		assertNotNull(itembyId);
		assertItem(itembyId, title, description, postedBy, status, price);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void deleteItem() throws Exception {
		// setup
		String title = "test item in the house";
		String description = "about this test...s";
		BigDecimal postedBy = BigDecimal.ONE;
		String status = "open";
		BigDecimal price = BigDecimal.TEN;

		// execute
		dao.create(title, description, postedBy, status, price);
		Map<String, Object> testItem = dao.getItemByName(title, postedBy);
		dao.deleteItem((BigDecimal) testItem.get("ITEM_ID"));
		dao.getItembyId((BigDecimal) testItem.get("ITEM_ID"));

		// assert - Exception
	}

	@Test
	public void updateItem() throws Exception {
		// setup
		String title = "test item in the house";
		String newTitle = "new Title";
		String description = "about this test...s";
		String newDescription = "nothing";
		BigDecimal postedBy = BigDecimal.ONE;
		BigDecimal purchasedBy = new BigDecimal("2");
		String status = "open";
		String newStatus = "closed";
		BigDecimal price = BigDecimal.TEN;
		BigDecimal newPrice = new BigDecimal("15");

		// execute
		Boolean created = dao.create(title, description, postedBy, status, price);
		Map<String, Object> testItem = dao.getItemByName(title, postedBy);
		BigDecimal itemId = (BigDecimal) testItem.get("ITEM_ID");
		dao.updateItem(itemId, newTitle, newDescription, purchasedBy, newPrice, newStatus);
		Map<String, Object> itembyId = dao.getItembyId((BigDecimal) itemId);

		// assert
		assertTrue(created);
		assertNotNull(testItem);
		assertItem(testItem, title, description, postedBy, status, price);
		assertNotNull(itembyId);
		assertItem(itembyId, newTitle, newDescription, postedBy, newStatus, newPrice);
		assertEquals((BigDecimal) itembyId.get("item_bought_by"), purchasedBy);
	}

	private void assertItem(Map<String, Object> itemMap, String title, String description, BigDecimal postedBy,
			String status, BigDecimal price) {
		assertEquals((String) itemMap.get("item_TITLE"), title);
		assertEquals((String) itemMap.get("item_DESC"), description);
		assertEquals((BigDecimal) itemMap.get("item_posted_by"), postedBy);
		assertEquals((String) itemMap.get("item_STATUS"), status);
		assertEquals((BigDecimal) itemMap.get("item_PRICE"), price);
	}
}
