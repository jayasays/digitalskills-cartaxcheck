package com.digitalskills.cartaxcheck.pageobjects;

import com.digitalskills.cartaxcheck.util.ElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CarTaxDetailsPage extends Page {

    private ElementHelper elementHelper;

    @FindBy(xpath = "//dt[text()='Registration']/ancestor::dl/dd")
    private WebElement registration;

    @FindBy(xpath = "//dt[text()='Make']/ancestor::dl/dd")
    private WebElement make;

    @FindBy(xpath = "//dt[text()='Model']/ancestor::dl/dd")
    private WebElement model;

    @FindBy(xpath = "//dt[text()='Colour']/ancestor::dl/dd")
    private WebElement colour;

    @FindBy(xpath = "//dt[text()='Year']/ancestor::dl/dd")
    private WebElement year;

    public CarTaxDetailsPage(WebDriver driver) {
        super(driver);
        elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isDisplayed() {
        elementHelper.waitUntilVisibilityOf(registration);
        return registration.isDisplayed();
    }

    public String getRegistration() {
        return registration.getText();
    }

    public String getMake() {
        return make.getText();
    }

    public String getModel() {
        return model.getText();
    }

    public String getColor() {
        return colour.getText();
    }

    public String getYear() {
        return year.getText();
    }
}
