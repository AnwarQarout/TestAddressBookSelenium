package TestingAddressBookSuite;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class ChromeDriverInit {

    protected static ChromeDriver driver;


    @BeforeSuite
    public void ChromeDriverInitialize(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

    }
}
