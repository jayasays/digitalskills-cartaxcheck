package com.digitalskills.cartaxcheck;

import com.digitalskills.cartaxcheck.factory.WebDriverFactory;
import com.digitalskills.cartaxcheck.mapper.VehicleMapper;
import com.digitalskills.cartaxcheck.model.Vehicle;
import com.digitalskills.cartaxcheck.pageobjects.*;
import com.digitalskills.cartaxcheck.util.FileHelper;
import com.digitalskills.cartaxcheck.util.PageLoader;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test automation suite which does following:
 *
 * 1. Reads given input file: car_input.txt
 * 2. Extracts vehicle registration numbers based on pattern(s).
 * 3. Each number extracted from input file is fed to https://cartaxcheck.co.uk/
 *          (Peform Free Car Check)
 * 4. Compare the output returned by https://cartaxcheck.co.uk/ with given car_output.txt
 * 5. Highlight/fail the test for any mismatches.
 *
 **/
public class CarTaxCheckWebPageTest {

    private WebDriver webDriver;

    private PageLoader page;

    private CarTaxCheckPage carTaxCheckPage;

    private CarTaxDetailsPage detailsPage;

    @Before
    public void setup() {
        if (webDriver == null) {
            webDriver = WebDriverFactory.create();
        }

        if (page == null) {
            page = new PageLoader(webDriver);
        }

    }

    @Test
    public void checkVehicleCarTax() throws Exception {
        //Load car input file and read vehicle registration numbers
        List<String> vehicleRegistrationNumbers = FileHelper.getVehicleRegistrationNumbers();

        //Load expected car output file and create a map of vehicles.
        Map<String, Vehicle> expectedVehicles = FileHelper.getExpectedVehicleData();

        vehicleRegistrationNumbers.forEach(registrationNumber -> {
            //Loading Car Tax Check Page
            carTaxCheckPage = page.load(CarTaxCheckPage.class);
            assertTrue(carTaxCheckPage.isDisplayed());
            carTaxCheckPage.enterRegistrationNumber(registrationNumber);
            carTaxCheckPage.clickOnFreeCarCheckButton();

            //Check that registration number
            String actualRegistrationNumber = carTaxCheckPage.getRegistrationNumber().replaceAll("\\s", "");
            String expectedRegistrationNumber = registrationNumber.replaceAll("\\s", "");
            assertThat(actualRegistrationNumber, is(equalTo(expectedRegistrationNumber)));

            //Loading Car Details Page
            detailsPage = page.init(CarTaxDetailsPage.class);
            assertTrue(detailsPage.isDisplayed());

            //Verify car details with expected values from car_output.txt
            Vehicle actualVehicle = VehicleMapper.mapToActualVehicle(detailsPage);
            Vehicle expectedVehicle = expectedVehicles.get(actualVehicle.getRegistration());
            assertThat(actualVehicle, samePropertyValuesAs(expectedVehicle));
        });

    }
}
