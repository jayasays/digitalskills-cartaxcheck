package com.digitalskills.cartaxcheck.pageobjects;

import com.digitalskills.cartaxcheck.util.ElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CarTaxCheckPage extends Page {

    private static final String PAGE_TITLE = "Free Car Check";
    private static final String RELATIVE_PAGE_URL = "/";

    private ElementHelper elementHelper;

    @FindBy(id = "vrm-input")
    private WebElement registrationNumberField;

    @FindBy(xpath = "//button[text()='Free Car Check']")
    private WebElement freeCarCheckButton;

    public CarTaxCheckPage(WebDriver driver) {
        super(driver);
        elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isDisplayed() {
        String header = elementHelper.findElementByTagName("h1").getText();
        return header.equals(PAGE_TITLE);
    }

    public void enterRegistrationNumber(String number){
        registrationNumberField.sendKeys(number);
    }

    public String getRegistrationNumber() {
        return registrationNumberField.getAttribute("value");
    }

    public void clickOnFreeCarCheckButton() {
        elementHelper.waitUntilVisibilityOf(freeCarCheckButton);
        freeCarCheckButton.click();
    }

    @Override
    public String getRelativeUrl() {

        return RELATIVE_PAGE_URL;
    }
}
