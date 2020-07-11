package feature.steps;

import com.digitalskills.cartaxcheck.factory.WebDriverFactory;
import com.digitalskills.cartaxcheck.mapper.VehicleMapper;
import com.digitalskills.cartaxcheck.model.Vehicle;
import com.digitalskills.cartaxcheck.pageobjects.CarTaxCheckPage;
import com.digitalskills.cartaxcheck.pageobjects.CarTaxDetailsPage;
import com.digitalskills.cartaxcheck.util.FileHelper;
import com.digitalskills.cartaxcheck.util.PageLoader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.*;

public class CarTaxCheckSteps {

    private List<String> vehicleRegistrationNumbers;

    private List<Vehicle> actualVehicles;

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


    @Given("^I have vehicle registration numbers$")
    public void iHaveVehicleRegistrationNumbers() throws Exception {
        //Load car input file and read vehicle registration numbers
        vehicleRegistrationNumbers = FileHelper.getVehicleRegistrationNumbers();
        assertFalse(vehicleRegistrationNumbers.isEmpty());
    }

    @When("^I check each registration number on cartaxcheck\\.com$")
    public void iCheckEachRegistrationNumberOnCartaxcheckCom() {
        actualVehicles = new ArrayList<>();
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
            actualVehicles.add(actualVehicle);
        });

    }

    @Then("^I can verify tax details with expected values$")
    public void iCanVerifyTaxDetailsWithExpectedValues() {
        //Load expected car output file and create a map of vehicles.
        Map<String, Vehicle> expectedVehicles = FileHelper.getExpectedVehicleData();

        //Verify car details with expected values from car_output.txt
        actualVehicles.forEach(actualVehicle -> {
            Vehicle expectedVehicle = expectedVehicles.get(actualVehicle.getRegistration());
            assertThat(actualVehicle, samePropertyValuesAs(expectedVehicle));
        });
    }
}
