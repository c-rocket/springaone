package com.oracle.aone.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.oracle.aone.dao.ItemDao;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

	@Mock
	private ItemDao itemDao;

	@InjectMocks
	private ItemService itemService;

	@Test
	public void getItems() {
		// setup
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		items.add(new HashMap<String, Object>());
		Mockito.when(itemDao.getItems()).thenReturn(items);

		// execute
		List<Map<String, Object>> actualItems = itemService.getItems();

		// assert
		assertTrue(actualItems.size() > 0);
	}
}
