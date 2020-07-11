package com.digitalskills.cartaxcheck.util;

import com.digitalskills.cartaxcheck.pageobjects.Page;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;

import static com.digitalskills.cartaxcheck.util.StaticPropertyLoader.getProperty;
import static com.digitalskills.cartaxcheck.util.Validator.validateApplicationUrl;

public class PageLoader {

	public static final String BASE_APP_URL_KEY = "base.app.url";

	public static String baseApplicationUrl = validateApplicationUrl(getProperty(BASE_APP_URL_KEY));

	private WebDriver webDriver;

	public PageLoader(WebDriver webDriver) {

		this.webDriver = webDriver;
	}

	/**
	 * Loads the provided URL but without initializing a Page Object.
	 */
	public void load(String relativeUrl) {

		String url = baseApplicationUrl + relativeUrl;
		System.out.printf("Going to url: {%s} %n", url);
		webDriver.get(url);
	}

	/**
	 * Loads a page by initialising the provided Page class but if the Page
	 * defines a {@link Page#getRelativeUrl()} then it loads that URL before
	 */
	public <PageObject extends Page> PageObject load(Class<PageObject> pageObjectClass) {

		System.out.printf("Loading page: {%s} %n", pageObjectClass);
		PageObject page = instantiatePage(webDriver, pageObjectClass);
		if (StringUtils.isNotEmpty(page.getRelativeUrl())) {
			load(page.getRelativeUrl());
		}
		return page;
	}

	/**
	 * Use this to initialize a Page Object class but without loading a URL. Use
	 * this if you are already on the page URL and want to initialize a sub
	 * component/module
	 */
	public <PageObject extends Page> PageObject init(Class<PageObject> pageObjectClass) {

		return instantiatePage(webDriver, pageObjectClass);
	}

	/**
	 * Reloads the currently loaded page.
	 */
	public void reload() {

		webDriver.navigate().refresh();
	}

	private static <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {

		try {
			try {
				Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
				return constructor.newInstance(driver);
			} catch (NoSuchMethodException e) {
				return pageClassToProxy.newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
