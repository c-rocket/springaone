package com.oracle.aone.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.aone.service.OfferService;
import com.oracle.aone.util.BigDecimalUtil;

@Controller
public class OfferController {
	private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

	@Resource
	private OfferService service;

	@RequestMapping(value = "/offer/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOffers(@PathVariable(value = "itemId") BigDecimal itemId) {
		logger.info("Getting offers for: " + itemId);
		return service.getOffersForItem(itemId);
	}

	@RequestMapping(value = "/offer/{offerId}", method = RequestMethod.PUT)
	@ResponseBody
	public Boolean updateOffer(@PathVariable(value = "offerId") Integer offerIdInt,
			@RequestBody Map<String, Object> update) {
		String status = (String) update.get("p1");
		BigDecimal offerId = BigDecimalUtil.parse(offerIdInt);
		logger.info("Updating Offer: " + offerId);
		return service.updateOffer(offerId, status);
	}

	@RequestMapping(value = "/newOffer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createOffer(@RequestBody Map<String, Object> offer) {
		BigDecimal itemId = BigDecimalUtil.parse(offer.get("p1"));
		BigDecimal offerBy = BigDecimalUtil.parse(offer.get("p2"));
		BigDecimal amount = BigDecimalUtil.parse(offer.get("p3"));

		logger.info("Creating Offer: " + itemId + ", " + offerBy + ", " + amount);
		return service.createOffer(itemId, offerBy, amount);
	}
}
