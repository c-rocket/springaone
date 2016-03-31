package com.oracle.aone.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gravatar {
	private static final Logger logger = LoggerFactory.getLogger(Gravatar.class);

	public static String url(String message) {
		return "https://www.gravatar.com/avatar/" + md5Hex(message) + ".jpg?d=identicon";
	}

	private static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	private static String md5Hex(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(message.getBytes("CP1252")));
		} catch (NoSuchAlgorithmException e) {
			logger.error("No Such Algroithm Exception in Creating Gravatar", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Unsupported Encoding Exception in Creating Gravatar", e);
		}
		return null;
	}
}
