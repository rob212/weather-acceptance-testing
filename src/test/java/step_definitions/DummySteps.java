package step_definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class DummySteps {

    private int total = 0;

    @Given("^some fake setup$")
    public void someFakeSetup() throws Throwable {
        // do nothing, just a dummy placeholder
    }

    @When("^we sum (\\d+) and (\\d+)$")
    public void weSomeAnd(int a, int b) throws Throwable {
        this.total = a + b;
    }

    @Then("^we get (\\d+)$")
    public void weGet(int expected) throws Throwable {
        assertEquals(total, expected);
    }

}
