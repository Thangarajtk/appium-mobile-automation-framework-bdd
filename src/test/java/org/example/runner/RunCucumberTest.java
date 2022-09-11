package org.example.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.example.driver.factory.DriverFactory;
import org.example.driver.manager.DriverManager;
import org.example.enums.ConfigJson;
import org.example.enums.MobilePlatformName;
import org.example.utils.AppiumServerManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.example.utils.configloader.JsonParser.getConfig;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"
        , "summary"
        , "html:target/cucumber/report.html",
        "me.jvt.cucumber.report.PrettyReports:target/cucumber"},
        features = {"src/test/resources/feature"},
        glue = {"org.example.stepdefinitions"},
        monochrome = true,
        tags = "@test"
)
public class RunCucumberTest {

    @BeforeClass
    public static void initialize() {
        AppiumServerManager.startAppiumServer();
        if (Objects.isNull(DriverManager.getDriver())) {
            DriverFactory.initializeDriver(MobilePlatformName
                    .valueOf((System.getProperty("platformName") != null ? System.getProperty("platformName") :
                            getConfig(ConfigJson.PLATFORM)).toUpperCase()));
        }
    }

    @AfterClass
    public static void quit() {
        if (Objects.nonNull(DriverManager.getDriver())) {
            DriverFactory.quitDriver();
        }
        AppiumServerManager.stopAppiumServer();
    }
}