import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = {"step_definitions"},
        format = {"pretty", "html:target/cucumber-reports/cucumber-pretty"})
public class RunCalculatorTest {
}
