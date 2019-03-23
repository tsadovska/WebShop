import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TotalSumCheck {
    private final By DRESSES = By.xpath(".//div[@id='block_top_menu']/ul/li/a[contains(@title, 'Dresses')]");
    private final By EVENING_DRESSES_IN_BLOCK_TOP_MENU = By.xpath(".//div[@id='categories_block_left']//a[contains(@title, 'evening')]");
    private final By DRESSES_ADITIONAL_INFO = By.xpath(".//div[contains(@class, 'product-container')]");
    private final By PRICE = By.xpath(".//div[contains(@class, 'price')]//span[@id='our_price_display']");

    private final By ADD_TO_CART = By.xpath(".//p[@id='add_to_cart']//button");
    private final By PROCEED_TO_CHEKOUT = By.xpath(".//div[contains(@class, 'layer_cart_cart')]//a[contains(@class, 'btn btn-default')]");
    private final By TOTAL_SUM = By.xpath(".//td[@id='total_product']");

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

        String eveningDressPriceBeforeAdditingInCartText = driver.findElement(PRICE).getText().substring(1);
        double eveningDressPriceBeforeAdditingInCartNumber = Double.parseDouble(eveningDressPriceBeforeAdditingInCartText);

        driver.findElement(ADD_TO_CART).click();

        //https://stackoverflow.com/questions/11736027/webdriver-wait-for-element-using-java
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement proceedToCheckoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(PROCEED_TO_CHEKOUT));
        proceedToCheckoutButton.click();

        driver.get("http://automationpractice.com/index.php");
        driver.findElement(DRESSES).click();
        driver.findElement(DRESSES_ADITIONAL_INFO).click();

        String additionalDressPriceBeforeAdditingInCartText = driver.findElement(PRICE).getText().substring(1);
        double additionalDressPriceBeforeAdditingInCartNumber = Double.parseDouble(additionalDressPriceBeforeAdditingInCartText);

        driver.findElement(ADD_TO_CART).click();
        WebDriverWait waitAdditional = new WebDriverWait(driver, 20);
        WebElement proceedToCheckoutAdditionalButton = waitAdditional.until(ExpectedConditions.visibilityOfElementLocated(PROCEED_TO_CHEKOUT));
        proceedToCheckoutAdditionalButton.click();

        String totalSumInCart = driver.findElement(TOTAL_SUM).getText().substring(1);
        double totalSumInCartNumber = Double.parseDouble(totalSumInCart);


        Assertions.assertEquals(eveningDressPriceBeforeAdditingInCartNumber + additionalDressPriceBeforeAdditingInCartNumber, totalSumInCartNumber,0.01);

    }

    @AfterEach
    public void closeBrowser() {
        driver.close();
    }
}
