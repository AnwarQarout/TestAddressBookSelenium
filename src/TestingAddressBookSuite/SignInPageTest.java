package TestingAddressBookSuite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class SignInPageTest extends ChromeDriverInit {
    final private static String badPassword = "badPassword123";
    final private static String badUsername = "badUsername123@gmail.com";
    final private static String invalidEmail = "badusername";
    final private  String validUsername = "anwarTrue@gmail.com";
    final  private String validPassword = "root1234";
     private ChromeDriver driver;

    public SignInPageTest(){

    }

    @BeforeClass
    public void beforeClass(){
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();

    }

    @BeforeMethod
    public void beforeMethod(){
        driver.get("http://a.testaddressbook.com/sign_in");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);

    }

    @AfterClass
   public void afterClass(){
        driver.close();
    }




    @Test
    public void testSignInPageTitle(){
        String actualTitle = driver.getTitle();
        Assert.assertEquals("Address Book - Sign In",actualTitle);
    }

    @Test
    public void testSignInPageElementsExist(){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement signInHeader = driver.findElement(By.xpath("//div[@id='clearance']//child::h2"));
        String actualText = signInHeader.getText();

        //assert that every element exists
        Assert.assertEquals(actualText,"Sign in");
        Assert.assertFalse(driver.findElements(By.xpath("//input[@type='email'][@placeholder='Email']")).isEmpty());
        Assert.assertFalse(driver.findElements(By.xpath("//input[@placeholder='Password'][@type='password']")).isEmpty());
        Assert.assertFalse(driver.findElements(By.xpath("//input[@type='submit'][@value='Sign in']")).isEmpty());
        Assert.assertFalse(driver.findElements(By.xpath("//a[@href='/sign_up'][text()='Sign up']")).isEmpty());



    }

    /* Method to enter empty credentials, or empty username */
    @Test
    public void testBadCredentialsInForm(){

        //submit without entering anything
        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']"));
        submitBtn.click();
        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'alert')][text()='Bad email or password.']")).isEmpty());

        //only enter password
        driver.get("http://a.testaddressbook.com/sign_in");

        WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password'][@type='password']"));
        passwordField.sendKeys(badPassword);

        submitBtn = driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'alert')][text()='Bad email or password.']")).isEmpty());

        //enter invalid username and password
        driver.get("http://a.testaddressbook.com/sign_in");

        WebElement usernameField = driver.findElement(By.xpath("//input[@type='email'][@placeholder='Email']"));
        usernameField.sendKeys(badUsername);

        passwordField = driver.findElement(By.xpath("//input[@placeholder='Password'][@type='password']"));
        passwordField.sendKeys(badPassword);

        submitBtn = driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'alert')][text()='Bad email or password.']")).isEmpty());

        // Only enter username
        driver.get("http://a.testaddressbook.com/sign_in");
        usernameField = driver.findElement(By.xpath("//input[@type='email'][@placeholder='Email']"));
        usernameField.sendKeys(badUsername);

        submitBtn = driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'alert')][text()='Bad email or password.']")).isEmpty());

        // enter invalid username with no @ in it
        driver.get("http://a.testaddressbook.com/sign_in");
        usernameField = driver.findElement(By.xpath("//input[@type='email'][@placeholder='Email']"));
        usernameField.sendKeys(invalidEmail);

        submitBtn = driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']"));
        submitBtn.click();

        Assert.assertEquals("http://a.testaddressbook.com/sign_in",driver.getCurrentUrl());

    }

    /* Only enter a username */
    @Test
    public void clickSignUpButton(){
        WebElement signUpBtn = driver.findElement(By.xpath("//a[@href='/sign_up'][text()='Sign up']"));
        signUpBtn.click();
        Assert.assertEquals("http://a.testaddressbook.com/sign_up",driver.getCurrentUrl());

        WebElement usernameField = driver.findElement(By.xpath("//input[@type='email'][@placeholder='Email']"));
        usernameField.sendKeys(validUsername);

        WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password'][@type='password']"));
        passwordField.sendKeys(validPassword);

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit'][@value='Sign up']"));
        submitBtn.click();

        Assert.assertEquals("http://a.testaddressbook.com/users",driver.getCurrentUrl());




    }

    @Test
    public void signIn(){
        WebElement usernameField = driver.findElement(By.xpath("//input[@type='email'][@placeholder='Email']"));
        usernameField.sendKeys(validUsername);

        WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password'][@type='password']"));
        passwordField.sendKeys(validPassword);

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']"));
        submitBtn.click();

        Assert.assertEquals("http://a.testaddressbook.com/",driver.getCurrentUrl());

    }

}
