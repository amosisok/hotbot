package autofill.com.amosk;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;

//https://www.vancityoriginal.com/1996-retro-nuptse-jacket-black/

public class Autocheckout {

    Options options = Options.getInstance();
    String dir = "/Users/" + System.getProperty("user.name") + "/Desktop";

    public void runAutocheckout(UserInfo userInfo, ItemInfo itemInfo) {
//        System.setProperty("webdriver.gecko.driver",  "/Users/amosk/Desktop/fill/geckodriver");
        System.setProperty("webdriver.gecko.driver",  dir + "/hotbot/geckodriver");

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

        if(link.contains("adidas")) {
            adidas(driver, userInfo, itemInfo);
        }

        else if(link.contains("vancityoriginal")) {
            vancityOriginal(driver, userInfo, itemInfo);
        }
    }

    public void adidas(WebDriver driver, UserInfo userInfo, ItemInfo itemInfo) {
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

        WebElement cart = driver.findElement(By.xpath("//span[contains(.,'Add To Bag')]"));
//        jse.executeScript("arguments[0].scrollIntoView();", cart);

        cart.click();

        driver.get("https://www.adidas.ca/en/delivery");

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
        driver.close();
    }

    public void vancityOriginal(WebDriver driver, UserInfo userInfo, ItemInfo itemInfo) {
//        WebElement size = driver.findElement(By.xpath("//label[@class='form-option'][contains(.,'Medium')]"));

        WebElement item;
        if(!itemInfo.shoeSize.isEmpty()) {
            item = driver.findElement(By.xpath("//label[@class='form-option'][contains(.,'" + itemInfo.shoeSize + "')]"));
        }

        else {
            String itemSize = convertSizeVancity(itemInfo.clothingSize);
            item = driver.findElement(By.xpath("//label[@class='form-option'][contains(.,'" + itemSize + "')]"));
        }
        item.click();

        WebElement cart = driver.findElement(By.xpath("//input[contains(@value,'Add to Cart')]"));
        cart.click();

//        driver.get("https://www.vancityoriginal.com/checkout.php");

//        WebElement checkout = driver.findElement(By.xpath("//a[contains(.,'Proceed to checkout')]"));
//        checkout.click();
    }

    public String convertSizeVancity(String s) {
        if(s.contains("S") || s.contains("s") || s.contains("small")) {
            s = "Small";
        }

        else if(s.contains("M") || s.contains("m") || s.contains("medium")) {
            s = "Medium";
        }

        else if(s.contains("L") || s.contains("l") || s.contains("large")) {
            s = "Large";
        }
        return s;
    }
}
