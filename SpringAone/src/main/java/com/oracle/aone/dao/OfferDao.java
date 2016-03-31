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
public class OfferDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier(value = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> getOffersForItem(BigDecimal itemId) {
		String sql = "SELECT OFFERS.OFFER_ID,USERS.USER_NAME, USERS.USER_GRAVATAR, OFFERS.OFFER_AMOUNT "
				+ "from USERS, OFFERS where OFFERS.OFFER_BY = USERS.user_id and item_id =?";
		return jdbcTemplate.queryForList(sql, itemId);
	}

	public Boolean updateOffer(BigDecimal offerId, String status) {
		String sql = "UPDATE offers SET offer_status = ? where offer_id = ?";
		return jdbcTemplate.update(sql, status, offerId) > 0;
	}

	public Boolean createOffer(BigDecimal itemId, BigDecimal offerBy, BigDecimal amount) {
		String sql = "INSERT INTO offers (item_id, offer_by,offer_amount) VALUES (?, ?, ?)";
		return jdbcTemplate.update(sql, itemId, offerBy, amount) > 0;
	}

	public Map<String, Object> getCreatedOffer(BigDecimal itemId, BigDecimal offerBy, BigDecimal amount) {
		String sql = "SELECT ITEM_ID, OFFER_ID, OFFER_BY, OFFER_AMOUNT, OFFER_STATUS, TO_CHAR(OFFER_CREATE_DATE) "
				+ "from OFFERS WHERE item_id=? AND offer_by=? AND offer_amount=?";
		return jdbcTemplate.queryForMap(sql, itemId, offerBy, amount);
	}

}
