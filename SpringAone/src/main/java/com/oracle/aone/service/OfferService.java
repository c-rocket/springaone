package com.oracle.aone.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.aone.dao.OfferDao;

@Service
@Transactional
public class OfferService {

	@Resource
	private OfferDao dao;

	public List<Map<String, Object>> getOffersForItem(BigDecimal itemId) {
		return dao.getOffersForItem(itemId);
	}

	public Boolean updateOffer(BigDecimal offerId, String status) {
		return dao.updateOffer(offerId, status);
	}

	public Map<String, Object> createOffer(BigDecimal itemId, BigDecimal offerBy, BigDecimal amount) {
		if (dao.createOffer(itemId, offerBy, amount)) {
			return dao.getCreatedOffer(itemId, offerBy, amount);
		}
		return null;
	}

}
