package com.digitalskills.cartaxcheck.pageobjects;

import com.digitalskills.cartaxcheck.util.PageLoader;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

/**
 * Super class for all page objects. It contains references to the Webdriver and
 * some common methods, which can be overriden by subclasses.
 */
public abstract class Page {
    protected WebDriver driver;

    protected PageLoader page;

    public Page(WebDriver driver) {
        this.driver = driver;
        page = new PageLoader(driver);
    }

    /**
     * Subclasses can override this method to check if the particular page,
     * module or component is properly displayed.
     */
    public boolean isDisplayed() {

        return true;
    }

    public String getCurrentUrl() {

        return driver.getCurrentUrl();

    }

    /**
     * This is the relative url to be used to navigate directly to a Page.
     * Concrete Page Object classes, which has an associated url, need to
     * override this method and provide that relative URL. Do NOT override this
     * method for pages which are not supposed to be DIRECTLY navigatable
     */
    public String getRelativeUrl() {

        return StringUtils.EMPTY;
    }

    public String getCurrrentURL() {
        return driver.getCurrentUrl();
    }

    public void close() {
        driver.close();
    }
}
