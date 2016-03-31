package com.oracle.aone.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
public class OfferDaoTest {
	@Resource
	private OfferDao dao;

	@Test
	public void createAndGetOffer() throws Exception {
		// setup
		BigDecimal itemId = new BigDecimal("100");
		BigDecimal offerBy = new BigDecimal("1");
		BigDecimal amount = new BigDecimal("10");

		// execute
		dao.createOffer(itemId, offerBy, amount);
		Map<String, Object> offer = dao.getCreatedOffer(itemId, offerBy, amount);
		List<Map<String, Object>> offers = dao.getOffersForItem(itemId);

		// assert
		assertTrue(offers.size() == 1);
		assertEquals((BigDecimal) offer.get("ITEM_ID"), itemId);
		assertEquals((BigDecimal) offer.get("OFFER_BY"), offerBy);
		assertEquals((BigDecimal) offer.get("OFFER_AMOUNT"), amount);
		assertNull((String) offer.get("OFFER_STATUS"));
	}

	@Test
	public void updateOffer() throws Exception {
		// setup
		BigDecimal itemId = new BigDecimal("101");
		BigDecimal offerBy = new BigDecimal("1");
		BigDecimal amount = new BigDecimal("10");

		// execute
		dao.createOffer(itemId, offerBy, amount);
		Map<String, Object> offer = dao.getCreatedOffer(itemId, offerBy, amount);
		BigDecimal offerId = (BigDecimal) offer.get("OFFER_ID");
		dao.updateOffer(offerId, "accepted");
		Map<String, Object> updatedOffer = dao.getCreatedOffer(itemId, offerBy, amount);

		// assert
		assertEquals((BigDecimal) updatedOffer.get("ITEM_ID"), itemId);
		assertEquals((BigDecimal) updatedOffer.get("OFFER_BY"), offerBy);
		assertEquals((BigDecimal) updatedOffer.get("OFFER_AMOUNT"), amount);
		assertEquals((String) updatedOffer.get("OFFER_STATUS"), "accepted");
	}
}
