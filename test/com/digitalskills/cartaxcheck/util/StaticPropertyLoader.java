package com.digitalskills.cartaxcheck.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

/**
 * This class loads properties from a file.
 */
public class StaticPropertyLoader {

	private static Properties props = loadPropertiesFromFile();

	private static Properties loadPropertiesFromFile() {

		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("resources/cartaxchecktest.properties");

			// load a properties file
			prop.load(input);
			prop.putAll(System.getProperties());
			return prop;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getProperty(String key) {

		String value = props.getProperty(key);
		System.out.printf("Loading property with key/value: {%s}/{%s} %n", key, value);
		if (value == null) {
			throw new RuntimeException(format("Property for key: %s was not found", key));
		}
		return value;
	}
}
