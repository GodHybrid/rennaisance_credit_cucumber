import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/test/java/Features" },
        glue = {"godhybrid.credits.Glue"},
        plugin = {"io.qameta.allure.cucumber3jvm.AllureCucumber3Jvm"}
        //tags = {"@all", "@success", "@failing", "@scenario_1", "@scenario_2"}
)

public class CucumberRunner {
}
