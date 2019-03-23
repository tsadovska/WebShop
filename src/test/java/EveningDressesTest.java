import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


/*
    Test is created to check if evening dresses can be added to cart.
    Steps:
    1. Go to homepage: http://automationpractice.com/index.php
    2. Click Dresses
    3. Click Evening dresses
    4. Open details
    5. Add to Cart
    6. Check, that dress is added
 */

public class EveningDressesTest {

    private final By DRESSES = By.xpath(".//div[@id='block_top_menu']/ul/li/a[contains(@title, 'Dresses')]");
    private final By EVENING_DRESSES_IN_BLOCK_TOP_MENU = By.xpath(".//div[@id='categories_block_left']//a[contains(@title, 'evening')]");
    private final By DRESSES_ADITIONAL_INFO = By.xpath(".//div[contains(@class, 'product-container')]");
    //private final By PRICE = By.xpath(".//div[contains(@class, 'price')]//span[@id='our_price_display']");

    private final By DRESS_MODEL_IN_ADDITIONAL_INFO_CARD = By.xpath(".//p[@id='product_reference']/span");
    private final By DRESS_SIZE_IN_ADDITIONAL_INFO_CARD = By.xpath(".//div[@id='uniform-group_1']//span");
    private final By DRESS_COLOR_IN_ADITIONAL_INFO_CARD = By.xpath(".//fieldset[@class='attribute_fieldset']//li[@class='selected']//a");

    private final By ADD_TO_CART = By.xpath(".//p[@id='add_to_cart']//button");
    private final By PROCEED_TO_CHEKOUT = By.xpath(".//div[contains(@class, 'layer_cart_cart')]//a[contains(@class, 'btn btn-default')]");

    private final By DRESS_MODEL_IN_CART = By.xpath(".//td[@class = 'cart_description']//small[contains(@class, 'cart_ref')]");
    private final By DRESS_SIZE_AND_COLOR_IN_CART = By.xpath(".//td[@class = 'cart_description']//small//a");

    private WebDriver driver;



    @Test
    public void isEveningDressAddedToCartUsingBlockTopMenu() {
        System.setProperty("webdriver.gecko.driver", "d:/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php");
        driver.findElement(DRESSES).click();
        driver.findElement(EVENING_DRESSES_IN_BLOCK_TOP_MENU).click();
        driver.findElement(DRESSES_ADITIONAL_INFO).click();

        //Save information about product
        String dressModelInAdditionalInfoCard = driver.findElement(DRESS_MODEL_IN_ADDITIONAL_INFO_CARD).getText();
        String dressSizeInAdditionalInfoCard = driver.findElement(DRESS_SIZE_IN_ADDITIONAL_INFO_CARD).getText();
        String dressColorInAdditionalInfoCard = driver.findElement(DRESS_COLOR_IN_ADITIONAL_INFO_CARD).getAttribute("title");
        driver.findElement(ADD_TO_CART).click();


        //https://stackoverflow.com/questions/11736027/webdriver-wait-for-element-using-java
        WebDriverWait wait = new WebDriverWait(driver,20);
        WebElement proceedToCheckoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(PROCEED_TO_CHEKOUT));
        proceedToCheckoutButton.click();

        String dressModelInCart = driver.findElement(DRESS_MODEL_IN_CART).getText();
        Assertions.assertEquals("SKU : " + dressModelInAdditionalInfoCard, dressModelInCart);

        String dressSizeAndColorInCart = driver.findElement(DRESS_SIZE_AND_COLOR_IN_CART).getText();
        Assertions.assertEquals("Color : " + dressColorInAdditionalInfoCard + ", Size : " + dressSizeInAdditionalInfoCard, dressSizeAndColorInCart);

    }


}
