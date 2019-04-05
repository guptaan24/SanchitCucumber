package com.cucumber.StepDefinitions;

import com.cucumber.PageModel.DriverInitializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
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

    @When("^I call the duration Service, Duration should be greater than \"([^\"]*)\" and less than \"([^\"]*)\"")
    public void validateDuration(String lessThan, String greaterThan) throws Throwable {

        String url = "https://www.tourradar.com/pw_serp/page_load";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        String urlParameters = "duration="+lessThan+"-"+greaterThan+"&order=popularity&page=1&type=continent&id=230";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(response.toString());
        JsonNode jsrray=actualObj.get("parameters");

        if(jsrray.isArray()){
            for (final JsonNode objNode : jsrray) {
                assertThat(Integer.parseInt(objNode.get("value").textValue())>=Integer.parseInt(lessThan));
                assertThat(Integer.parseInt(objNode.get("value").textValue())<=Integer.parseInt(greaterThan));
            }
        }

    }
}