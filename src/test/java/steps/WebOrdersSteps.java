package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.WebOrdersHomePage;
import pages.WebOrdersLoginPage;
import utilities.BrowserUtils;
import utilities.Configuration;
import utilities.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebOrdersSteps {

    WebDriver driver = Driver.getDriver();
    WebOrdersLoginPage webOrdersLoginPage = new WebOrdersLoginPage();
    WebOrdersHomePage webOrdersHomePage = new WebOrdersHomePage();
    List<Map<String,Object>> data=new ArrayList<>();

    @Given("User navigates to application")
    public void user_navigates_to_application() {
        driver.get(Configuration.getProperty("WebOrdersURL"));


    }

    @When("User log in with username {string} and password {string}")
    public void user_log_in_with_username_and_password(String username, String password) { //password="test"; username="Tester";
        webOrdersLoginPage.username.sendKeys(username);
        webOrdersLoginPage.password.sendKeys(password);
        webOrdersLoginPage.loginButton.click();

    }

    @Then("User validates that application is on homepage")
    public void user_validates_that_application_is_on_homepage() {
        String expectedTitle = "Web Orders";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
        driver.quit();
    }

    @Then("User validates error login message {string}")//Cucumber
    public void user_validates_error_login_message(String expectedErrorMessage) {//Selenium
        String actualErrorMessage = webOrdersLoginPage.errorMessage.getText();//Java
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage); //JUnit

    }

    @When("User clicks on Order module")
    public void user_clicks_on_Order_module() {
        webOrdersHomePage.orderButton.click();

    }

    @When("User provides product {string} and quantity {int}")
    public void user_provides_product_and_quantity(String product, Integer quantity) {
        BrowserUtils.selectDropdownByValue(webOrdersHomePage.chooseProduct, product);
        webOrdersHomePage.chooseQuantity.sendKeys(Keys.BACK_SPACE);
        webOrdersHomePage.chooseQuantity.sendKeys(quantity.toString() + Keys.ENTER);


    }

    @Then("User validates price total is calculated properly for quantity {int}")
    public void user_validates_price_total_is_calculated_properly_for_quantity(Integer quantity) {
        // <input id="shgagjjfy" name="sdd" value = "80" >
       String pricePerUnit = webOrdersHomePage.pricePerUnit.getAttribute("value"); // 80
        int discountAmount = Integer.parseInt(webOrdersHomePage.discountBox.getAttribute("value"));

        int expectedTotal=0;

        if(discountAmount!=0){
            expectedTotal = quantity * Integer.parseInt(pricePerUnit)*(100 -discountAmount)/100;

        }else{

            expectedTotal = quantity * Integer.parseInt(pricePerUnit); // 1*80=80
        }


        int actualTotal = Integer.parseInt(webOrdersHomePage.getTotal.getAttribute("value"));
        Assert.assertEquals(actualTotal,expectedTotal);


    }
    @When("User creates and order with data")
    public void user_creates_and_order_with_data(DataTable dataTable) {
        // Convert dataTable to list of maps -> List<Map<String,Object>>
        List<Map<String,Object>> data = dataTable.asMaps(String.class,Object.class);


        for(int i=0; i< data.size(); i++) {
            BrowserUtils.selectDropdownByValue(webOrdersHomePage.chooseProduct,data.get(i).get("Product").toString());
            webOrdersHomePage.chooseQuantity.sendKeys(Keys.BACK_SPACE);
            webOrdersHomePage.chooseQuantity.sendKeys(data.get(i).get("Quantity").toString());
            webOrdersHomePage.inputName.sendKeys(data.get(i).get("Customer name").toString());
            webOrdersHomePage.inputStreet.sendKeys(data.get(i).get("Street").toString());
            webOrdersHomePage.inputCity.sendKeys(data.get(i).get("City").toString());
            webOrdersHomePage.inputState.sendKeys(data.get(i).get("State").toString());
            webOrdersHomePage.inputZip.sendKeys(data.get(i).get("Zip").toString());
            webOrdersHomePage.visaCardBtn.click();
            webOrdersHomePage.inputCCNumber.sendKeys(data.get(i).get("Card Nr").toString());
            webOrdersHomePage.inputExpDate.sendKeys(data.get(i).get("Exp Date").toString());
            webOrdersHomePage.processBtn.click();


        }

    }

    @Then("User validates success message {string}")
    public void user_validates_success_message(String expectedResult) {
        String actualResult = webOrdersHomePage.successMessage.getText();
        Assert.assertEquals(actualResult,expectedResult);


    }
    @Then("User validates that created orders are in the list of all orders")
    public void user_validates_that_created_orders_are_in_the_list_of_all_orders() {

        int numberOfCreatedOrders = data.size();


        for(int i=0, r=numberOfCreatedOrders+1; i<numberOfCreatedOrders; i++,r--){
            List<WebElement> row = driver.findElements(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tr["+r+"]/td"));
            Assert.assertEquals(row.get(1).getText(),data.get(i).get("Customer name"));

        }

    }
}