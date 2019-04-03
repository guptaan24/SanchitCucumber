package com.cucumber.StepDefinitions;

import com.cucumber.PageModel.DriverInitializer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidateFilters {

    WebDriver webDriver = null;

    @Given("^I open browser$")
    public void openChrome() throws Throwable {
        webDriver = DriverInitializer.getDriver("firefox");
    }

    @When("^I navigate to Site")
    public void navigateToSite() throws Throwable {
        webDriver.get(DriverInitializer.getProperty("site.url"));
    }

    @When("^I filter on Departure Date \"([^\"]*)\"")
    public void filterOnDeparture(String filter) throws Throwable {
    WebElement we=webDriver.findElement(By.linkText(filter));
    webDriver.navigate().to(we.getAttribute("href"));
    }


    @Then("^Start Date should lie in Departure filter \"([^\"]*)\"")
    public void validateMonth(String month) throws Throwable {
        List<WebElement> webElements = webDriver.findElements(By.className("br__availability-wrapper-date"));
        try {
            for(WebElement element:webElements){
               assertThat(element.getText().contains(month.substring(0,2)));
            }
        } finally {
            webDriver.quit();
        }
    }
}