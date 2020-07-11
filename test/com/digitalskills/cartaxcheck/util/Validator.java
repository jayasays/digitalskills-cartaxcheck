package com.digitalskills.cartaxcheck.util;

import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Validator {

	public static String validateApplicationUrl(String applicationUrl) {

		if (isNotBlank(applicationUrl)) {
			try {
				new URL(applicationUrl);
			} catch (MalformedURLException e) {
				throw new RuntimeException("application.url had a malformed URL: " + applicationUrl);
			}
		} else {
			throw new RuntimeException("application.url is not available.");
		}

		return applicationUrl;

	}

}
