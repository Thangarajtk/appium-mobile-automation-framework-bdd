package org.example.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.example.driver.Drivers;
import org.example.driver.manager.DriverManager;
import org.example.enums.ConfigJson;
import org.example.enums.MobilePlatformName;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.Objects;
import java.util.Optional;

import static org.example.utils.AppiumServerManager.startAppiumServer;
import static org.example.utils.AppiumServerManager.stopAppiumServer;
import static org.example.utils.configloader.JsonParser.getConfig;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty",
  "summary",
  "html:target/cucumber-html-output/cucumber-html-report.html",
  "json:target/cucumber-json-output/cucumber.json"},
  features = {"src/test/resources/feature"},
  glue = {"org.example.stepdefinitions"},
  monochrome = true,
  tags = "@Login"
)
public class RunnerTest {

  @BeforeClass
  public static void setUp() {
    startAppiumServer();
    if (Objects.isNull(DriverManager.getDriver())) {
      Drivers.initializeDriver(MobilePlatformName.valueOf(
        Optional.ofNullable(System.getProperty("platformName"))
          .orElse(getConfig(ConfigJson.PLATFORM)).toUpperCase()));
    }
  }

  @AfterClass
  public static void tearDown() {
    if (Objects.nonNull(DriverManager.getDriver())) {
      Drivers.quitDriver();
    }
    stopAppiumServer();
  }
}
