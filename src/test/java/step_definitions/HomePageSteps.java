package step_definitions;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import cucumber.api.java.Before;

import static org.junit.Assert.assertEquals;


public class HomePageSteps {

    private WebDriver driver;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("^I have navigated to the \"([^\"]*)\"$")
    public void iHaveNavigatedToThe(String arg1) throws Throwable {
        driver.get("https://weather-acceptance.herokuapp.com/");
    }


    @Then("^the page title should be \"([^\"]*)\"$")
    public void thePageTitleShouldBe(String arg1) throws Throwable {
        assertEquals("5 Weather Forecast",  driver.getTitle());
    }

}
