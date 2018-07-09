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

    @When("^we some (\\d+) and (\\d+)$")
    public void weSomeAnd(int arg1, int arg2) throws Throwable {
        this.total = arg1 + arg2;
    }

    @Then("^we get (\\d+)$")
    public void weGet(int arg1) throws Throwable {
        assertEquals(total, 2);
    }

}
