package TestingAddressBookSuite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class MainPageTest extends ChromeDriverInit{
    ChromeDriver driver;

    public MainPageTest() {
    }

   /* @BeforeSuite
    public void beforeSuite(){
        driver = ChromeDriverInit();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
    }*/

    @BeforeClass
    public void beforeClass(){
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.get("http://a.testaddressbook.com/");
        driver.manage().window().maximize();
    }

  /* @AfterClass
    public void afterClass(){
        driver.close();
    }*/



    @Test
    public void testMainPageTitle() {
        String expectedTitle = "Address Book";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

    }

    @Test
    public void testMainPageHeaders(){
        WebElement mainHeader = driver.findElement(By.xpath("//div[@class='text-center']//child::h1"));

        String expectedMainHeader = "Welcome to Address Book";
        Assert.assertEquals(expectedMainHeader, mainHeader.getText());

        String expectedSubHeader = "A simple web app for showing off your testing";
        WebElement subHeader = driver.findElement(By.xpath("//div[@class='text-center']//descendant::h4"));
        Assert.assertEquals(expectedSubHeader, subHeader.getText());

    }
    @Test
    public void clickHomeButton(){
        WebElement homeLink = driver.findElement(By.xpath("//a[contains(@class,'nav')][@data-test='home']"));
        homeLink.click();

        testMainPageTitle();
        testMainPageHeaders();

        Assert.assertFalse(driver.findElements(By.xpath("//a[@data-test='home']//span[@class='sr-only']")).isEmpty());


    }

    @Test
   public void clickLoginButton(){
        WebElement signInLink = driver.findElement(By.xpath("//a[@id='sign-in'][text()='Sign in']"));
        signInLink.click();
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle,"Address Book - Sign In");

        Assert.assertFalse(driver.findElements(By.xpath("//a[@data-test='sign-in']//span[@class='sr-only']")).isEmpty());
    }
}
