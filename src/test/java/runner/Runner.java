package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber.json"},
        features = "src/test/resources/features",  // content root
        glue = "steps",
        tags = "@regression", // run only feature file that has either @HR-5 or @HR-6 tag.
        // tags = "@HR-5 and @HR-6", // run only those feature file that has both @HR-5 and @HR-6 tag.
        dryRun = false //  true -> give us unemplement steps
)
public class Runner {
}
