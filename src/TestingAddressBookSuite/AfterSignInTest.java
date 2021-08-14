package TestingAddressBookSuite;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AfterSignInTest extends ChromeDriverInit{
    private ChromeDriver driver;
    final private  String validUsername = "anwarTrue@gmail.com";
    final  private String validPassword = "root1234";
    final  private String invalidFirstName = "root1234";
    final private String invalidSecondName = "qarout232";
    final private String invalidZipCode = "12345sad";



    public AfterSignInTest(){

    }



    public void SignIn(@NotNull ChromeDriver driver){
            WebElement usernameField = driver.findElement(By.xpath("//input[@type='email'][@placeholder='Email']"));
            usernameField.sendKeys(validUsername);

            WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password'][@type='password']"));
            passwordField.sendKeys(validPassword);

            WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']"));
            submitBtn.click();

    }

    public void createValidAddressWithArgs(String name1, String name2){
        //System.out.println(flag);
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys(name1);

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys(name2);

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys("whatever");

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys("whatever");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys("12345");


        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertNotEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new"); //new
        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'alert')][text()='Address was successfully created.']")).isEmpty());
    }

    @BeforeClass
    public void beforeClass(){
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.get("http://a.testaddressbook.com/sign_in");
        SignIn(driver);
    }

    @AfterClass
    public void afterClass(){
        driver.close();
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.get("http://a.testaddressbook.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
    }

    @Test
    public void validateMainContents(){

        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/");
        WebElement mainHeader = driver.findElement(By.xpath("//div[@class='text-center']//child::h1"));

        String expectedMainHeader = "Welcome to Address Book";
        Assert.assertEquals(expectedMainHeader, mainHeader.getText());

        String expectedSubHeader = "A simple web app for showing off your testing";
        WebElement subHeader = driver.findElement(By.xpath("//div[@class='text-center']//descendant::h4"));
        Assert.assertEquals(expectedSubHeader, subHeader.getText());

        Assert.assertFalse(driver.findElements(By.xpath("//a[text()='Sign out']")).isEmpty());
        Assert.assertFalse(driver.findElements(By.xpath("//a[text()='Addresses']")).isEmpty());

        WebElement addressBtn = driver.findElement(By.xpath("//a[text()='Addresses']"));
        addressBtn.click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses");

        //Assert.assertFalse(driver.findElements(By.xpath("//span[@class='navbar-text'][text()='"+this.validUsername+"']")).isEmpty());

    }

    @Test
    public void testAddressBtn(){
        driver.get("http://a.testaddressbook.com/addresses");
        WebElement newAddress = driver.findElement(By.xpath("//a[contains(@class,'row')][@data-test='create']"));
        newAddress.click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new");
    }

    @Test
    public void createEmptyAddress(){
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();
        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new"); //new
    }

    @Test
    public void createInvalidFirstNameAddress(){
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys(invalidFirstName);

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys("whatever");

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys("whatever");

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys("whatever");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys("12345");

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new"); //new
    }

    @Test
    public void createInvalidLastNameAddress(){
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys("whatever");

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys(invalidSecondName);

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys("whatever");

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys("whatever");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys("12345");

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new"); //new

    }

    @Test
    public void createInvalidZipCodeAddress(){
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys("whatever");

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys("whatever");

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys("whatever");

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys("whatever");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys(invalidZipCode);

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new"); //new

    }

    @Test
    public void createInvalidAgeAddress(){
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys("whatever");

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys("whatever");

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys("whatever");

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys("whatever");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys("12345");

        WebElement age = driver.findElement(By.xpath("//input[@id='address_age']"));
        int randChoice = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        int randomNum;
        if(randChoice ==0) {
            randomNum = ThreadLocalRandom.current().nextInt(-20, 0 + 1);
        }
        else{
            randomNum = ThreadLocalRandom.current().nextInt(120, 1000 + 1);
        }

        age.click();
        age.sendKeys(Integer.toString(randomNum));

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new"); //new



    }

    @Test
    public void createInvalidBirthdayAddress(){
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys("whatever");

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys("whatever");

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys("whatever");

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys("whatever");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys("12345");

        /* Select Random Date */
        LocalDate date;
        int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);

        /* select randomly between two cases: either a very old, impossible birthdate, or a future birthdate. Both are invalid. */
        if(randomNum == 0) {
            date = LocalDate.of((int) ThreadLocalRandom.current().nextInt(0, 1900 + 1), (int) ThreadLocalRandom.current().nextInt(1, 12 + 1), (int) ThreadLocalRandom.current().nextInt(1, 31 + 1));
        }
        else {
            date = LocalDate.of((int) ThreadLocalRandom.current().nextInt(2021, 3000 + 1), (int) ThreadLocalRandom.current().nextInt(1, 12 + 1), (int) ThreadLocalRandom.current().nextInt(1, 31 + 1));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy");
        date.format(formatter);

        WebElement birthday = driver.findElement(By.xpath("//input[@type='date']"));
        birthday.sendKeys(date.toString());

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new"); //new

    }

    @Test
    public void createValidAddress(){
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys("whatever");

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys("whatever");

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys("whatever");

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys("whatever");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys("12345");


        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertNotEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses/new"); //new
        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'alert')][text()='Address was successfully created.']")).isEmpty());
    }

    @Test
    public void editAddressCorrectlyAfterCreation(){
        createValidAddress();
        WebElement edit = driver.findElement(By.xpath("//a[@data-test='edit'][text()='Edit']"));
        edit.click();
        Assert.assertFalse(driver.findElements(By.xpath("//h2[text()='Editing Address']")).isEmpty());
        WebElement updateBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        updateBtn.click();
        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'alert')][text()='Address was successfully updated.']")).isEmpty());
    }

    @Test
    public void editAddressIncorrectlyAfterCreation(){
        createValidAddress();
        WebElement edit = driver.findElement(By.xpath("//a[@data-test='edit'][text()='Edit']"));
        edit.click();
        Assert.assertFalse(driver.findElements(By.xpath("//h2[text()='Editing Address']")).isEmpty());

        String beforeEdit = driver.getCurrentUrl();

        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys(invalidFirstName);

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        firstName.click();
        firstName.clear();
        firstName.sendKeys(" ");


        WebElement updateBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        updateBtn.click();

        String afterEdit = driver.getCurrentUrl();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(beforeEdit,afterEdit);
    }

    @Test
    public void listAddressAfterCreation(){
        createValidAddressWithArgs("ABD","ABD");
        WebElement list = driver.findElement(By.xpath("//a[@data-test='list'][text()='List']"));
        list.click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses");
        Assert.assertFalse(driver.findElements(By.xpath("//tr//td[text()='ABD']")).isEmpty());
    }

    @Test
    public void listAddressBeforeCreation(){
        driver.get("http://a.testaddressbook.com/addresses/new");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys("hello");

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys("whatever");

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys("whatever");

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys("whatever");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys("12345");

        WebElement submitBtn = driver.findElement(By.xpath("//a[@data-test='list'][text()='List']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/addresses");
        Assert.assertFalse(driver.findElements(By.xpath("//tr//td[text()='hello']")).isEmpty());
    }






    /*
    public void createInvalidAddress(){


        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));

        WebElement address2 = driver.findElement(By.xpath("//input[@id=\"address_secondary_address\"]"));




        WebElement state = driver.findElement(By.xpath("//select[@id=\"address_state\"]"));
        state.click();
        List<WebElement> itemsInDropdown = driver.findElements(By.xpath("//select[@id=\"address_state\"]//option"));
        int listSize = itemsInDropdown.size();
        int randomIndex = ThreadLocalRandom.current().nextInt(0, listSize);
        itemsInDropdown.get(randomIndex).click();


        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));


        itemsInDropdown = driver.findElements(By.xpath("//select[@id=\"address_state\"]//option"));
        listSize = itemsInDropdown.size();
        randomIndex = ThreadLocalRandom.current().nextInt(0, listSize);
        itemsInDropdown.get(randomIndex).click();



        LocalDate date;
        int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);


        if(randomNum == 0) {
            date = LocalDate.of((int) ThreadLocalRandom.current().nextInt(0, 1900 + 1), (int) ThreadLocalRandom.current().nextInt(1, 12 + 1), (int) ThreadLocalRandom.current().nextInt(1, 31 + 1));
        }
        else {
            date = LocalDate.of((int) ThreadLocalRandom.current().nextInt(2021, 3000 + 1), (int) ThreadLocalRandom.current().nextInt(1, 12 + 1), (int) ThreadLocalRandom.current().nextInt(1, 31 + 1));
        }

        WebElement birthday = driver.findElement(By.xpath("//input[@type='date']"));
        birthday.sendKeys(date.toString());

        WebElement color = driver.findElement(By.xpath("//input[@type='color']"));

        WebElement age = driver.findElement(By.xpath("//input[@id='address_age']"));

        WebElement website = driver.findElement(By.xpath("//input[@id='address_website']"));

        WebElement phone = driver.findElement(By.xpath("//input[@type='tel']"));

    }*/




    /*@Test
    public void signOut(){
        WebElement signOutLink = driver.findElement(By.xpath("//a[text()='Sign out']"));
        signOutLink.click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/sign_in");
    }*/





}

