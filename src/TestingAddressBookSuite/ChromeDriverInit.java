package TestingAddressBookSuite;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class ChromeDriverInit {

    protected static ChromeOptions options;


    @BeforeSuite
    public void ChromeDriverInitialize(){
        options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");


    }
}
