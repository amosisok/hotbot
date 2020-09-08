package autofill.com.amosk;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;

public class Autocheckout {

    Options options = Options.getInstance();
    String dir = "/Users/" + System.getProperty("user.name") + "/Desktop";

    public void runAutocheckout(UserInfo userInfo, ItemInfo itemInfo) {
//        System.setProperty("webdriver.gecko.driver",  "/Users/amosk/Desktop/fill/geckodriver");
        System.setProperty("webdriver.gecko.driver",  dir + "/autocheckout/geckodriver");

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(true);

        WebDriver driver;
        if(options.isHeadless()) {
            driver = new FirefoxDriver(firefoxOptions);
        }
        else {
            driver = new FirefoxDriver();
        }
        String link = itemInfo.productName;
        driver.get(link);
//        driver.get("https://www.adidas.ca/en/ultraboost-dna-x-arsenal-shoes/FZ3621.html");

        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        }

        catch (NoAlertPresentException e) {}

        Actions action = new Actions(driver);
        action.sendKeys(Keys.SPACE).perform();

        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

        //get shoe size
        WebElement item;
        if(!itemInfo.shoeSize.isEmpty()) {
            item = driver.findElement(By.className("sizes___3Stmf")).findElement(By.xpath("//button[@class='gl-label size___TqqSo']")).findElement(
                    By.xpath("//span[text()='" + itemInfo.shoeSize + "']"));
        }

        else {
            item = driver.findElement(By.className("sizes___3Stmf")).findElement(By.xpath("//button[@class='gl-label size___TqqSo']")).findElement(
                    By.xpath("//span[text()='" + itemInfo.clothingSize + "']"));
        }
        item.click();

//        JavascriptExecutor jse = (JavascriptExecutor)driver;

        WebElement cart = driver.findElement(By.xpath("(//span[@class='gl-cta__content'][contains(.,'Add To Bag')])[2]"));
//        jse.executeScript("arguments[0].scrollIntoView();", cart);

        cart.click();

        WebElement checkOut = driver.findElement(By.xpath("//span[@class='gl-cta__content'][contains(.,'Checkout')]"));
        checkOut.click();

        driver.manage().timeouts().implicitlyWait(2500, TimeUnit.MILLISECONDS);


        WebElement firstName = driver.findElement(By.name("firstName"));
        firstName.sendKeys(userInfo.firstName);

        WebElement lastName = driver.findElement(By.name("lastName"));
        lastName.sendKeys(userInfo.lastName);

        WebElement address1 = driver.findElement(By.name("address1"));
        address1.sendKeys(userInfo.address1);

        WebElement city = driver.findElement(By.name("city"));
        city.sendKeys(userInfo.city);

        WebElement dropDown = driver.findElement(By.xpath("//button[@type='button'][contains(.,'Province')]"));
        dropDown.click();
        WebElement province = dropDown.findElement(By.xpath("//button[@class='gl-dropdown-custom__option'][contains(.,'" + userInfo.province + "')]"));
        province.click();

        WebElement zipcode = driver.findElement(By.name("zipcode"));
        zipcode.sendKeys(userInfo.postalCode);

        WebElement phoneNumber = driver.findElement(By.name("phoneNumber"));
        phoneNumber.sendKeys(userInfo.phoneNumber);

        WebElement emailAddress = driver.findElement(By.name("emailAddress"));
        emailAddress.sendKeys(userInfo.email);

        WebElement checkBox = driver.findElement(By.xpath("(//input[contains(@type,'checkbox')])[2]"));
        checkBox.click();

        WebElement pay = driver.findElement(By.xpath("//button[@type='button'][contains(.,'Review & Pay')]"));
        pay.click();

        driver.manage().timeouts().implicitlyWait(11000, TimeUnit.MILLISECONDS);

        WebElement cardNumber = driver.findElement(By.name("card-number"));
        cardNumber.sendKeys(userInfo.cardNumber);

        WebElement cardName = driver.findElement(By.name("name"));
        cardName.sendKeys(userInfo.cardName);

        WebElement cardExpiration = driver.findElement(By.name("expiry"));
        cardExpiration.sendKeys(userInfo.cardExpiration);

        WebElement cardCCV = driver.findElement(By.xpath("//input[@id = 'security-number-field']"));
        cardCCV.sendKeys(userInfo.cardCVV);

        WebElement placeOrder = driver.findElement(By.xpath("//span[@class='gl-cta__content'][contains(.,'Place Order')]"));
        placeOrder.click();
//        driver.close();
    }
}
