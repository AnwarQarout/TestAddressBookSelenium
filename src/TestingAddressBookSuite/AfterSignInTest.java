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
    final private String invalidAddress = "";
    final private String invalidCity = "";

    final  private String validFirstName = "anwar";
    final private String validSecondName = "qarout";
    final private String validZipCode = "12345";
    final private String validAddress = "Ramallah";
    final private String validCity = "Ramallah";

    final private String URL = "http://a.testaddressbook.com/";
    final private String addressURL = "http://a.testaddressbook.com/addresses";
    final private String newAddressURL = "http://a.testaddressbook.com/addresses/new";





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

    public void createValidAddressWithArgs(String name1, String name2,String addr, String City, String zip){
        //System.out.println(flag);
        driver.get(newAddressURL);
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"address_first_name\"]"));
        firstName.click();
        firstName.sendKeys(name1);

        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"address_last_name\"]"));
        lastName.click();
        lastName.sendKeys(name2);

        WebElement address1 = driver.findElement(By.xpath("//input[@id=\"address_street_address\"]"));
        address1.click();
        address1.sendKeys(addr);

        WebElement city = driver.findElement(By.xpath("//input[@id='address_city']"));
        city.click();
        city.sendKeys(City);

        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"address_zip_code\"]"));
        zipCode.click();
        zipCode.sendKeys(zip);


      /*  WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertNotEquals(driver.getCurrentUrl(),newAddressURL); //new
        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'alert')][text()='Address was successfully created.']")).isEmpty());*/
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
        driver.get(URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
    }

    @Test
    public void validateMainContents(){

        //a[@data-test='home']//span[@class='sr-only']
        Assert.assertFalse(driver.findElements(By.xpath("//a[@data-test='home']//span[@class='sr-only']")).isEmpty());

        Assert.assertEquals(driver.getCurrentUrl(),URL);
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
        Assert.assertEquals(driver.getCurrentUrl(),addressURL);
        Assert.assertFalse(driver.findElements(By.xpath("//a[@data-test='addresses']//span[@class='sr-only']")).isEmpty());

        //Assert.assertFalse(driver.findElements(By.xpath("//span[@class='navbar-text'][text()='"+this.validUsername+"']")).isEmpty());

    }

    @Test
    public void testAddressBtn(){
        driver.get(addressURL);
        WebElement newAddress = driver.findElement(By.xpath("//a[contains(@class,'row')][@data-test='create']"));
        newAddress.click();
        Assert.assertEquals(driver.getCurrentUrl(),newAddressURL);
    }

    @Test
    public void createEmptyAddress(){
        driver.get(newAddressURL);
        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();
        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),newAddressURL); //new
    }

    @Test
    public void createInvalidFirstNameAddress(){
        createValidAddressWithArgs(invalidFirstName,validSecondName,validAddress,validCity,validZipCode);

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),newAddressURL); //new
    }

    @Test
    public void createInvalidLastNameAddress(){
        createValidAddressWithArgs(validFirstName,invalidSecondName,validAddress,validCity,validZipCode);

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),newAddressURL); //new

    }

    @Test
    public void createInvalidZipCodeAddress(){
        createValidAddressWithArgs(validFirstName,validSecondName,validAddress,validCity,invalidZipCode);

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),newAddressURL); //new

    }

    @Test
    public void createInvalidAgeAddress(){

        int randChoice = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        int randomNum;
        if(randChoice ==0) {
            randomNum = ThreadLocalRandom.current().nextInt(-20, 0 + 1);
        }
        else{
            randomNum = ThreadLocalRandom.current().nextInt(120, 1000 + 1);
        }

        createValidAddressWithArgs(validFirstName,validSecondName,validAddress,validCity,validZipCode);
        WebElement age = driver.findElement(By.xpath("//input[@id='address_age']"));
        age.click();
        age.sendKeys(Integer.toString(randomNum));

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),newAddressURL); //new

    }

    @Test
    public void createInvalidBirthdayAddress(){


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

        createValidAddressWithArgs(validFirstName,validSecondName,validAddress,validCity,validZipCode);

        WebElement birthday = driver.findElement(By.xpath("//input[@type='date']"));
        birthday.sendKeys(date.toString());

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertFalse(driver.findElements(By.xpath("//ul")).isEmpty());
        Assert.assertEquals(driver.getCurrentUrl(),newAddressURL); //new

    }

    @Test
    public void createValidAddress(){
        createValidAddressWithArgs(validFirstName,validSecondName,validAddress,validCity,validZipCode);

        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        Assert.assertNotEquals(driver.getCurrentUrl(),newAddressURL); //new
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
        createValidAddressWithArgs(validFirstName,validSecondName,validAddress,validCity,validZipCode);
        WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();

        WebElement list = driver.findElement(By.xpath("//a[@data-test='list'][text()='List']"));
        list.click();

        Assert.assertEquals(driver.getCurrentUrl(),addressURL);
        Assert.assertFalse(driver.findElements(By.xpath("//tr//td[text()='"+validFirstName+"']")).isEmpty());
    }

    @Test
    public void listAddressBeforeCreation(){
        createValidAddressWithArgs(validFirstName,validSecondName,validAddress,validCity,validZipCode);

        WebElement submitBtn = driver.findElement(By.xpath("//a[@data-test='list'][text()='List']"));
        submitBtn.click();

        Assert.assertEquals(driver.getCurrentUrl(),addressURL);
        Assert.assertFalse(driver.findElements(By.xpath("//tr//td[text()='"+validFirstName+"']")).isEmpty());
    }



    @Test
    public void destroyAddress(){
        driver.get(addressURL);
        WebElement firstNameOfFirstAddress = driver.findElement(By.xpath("//table//tbody//tr//td"));
        String text = firstNameOfFirstAddress.getText();

        WebElement destroy = driver.findElement(By.xpath("//table//tbody//tr//td//a[@data-method='delete']"));
        destroy.click();

        driver.switchTo().alert().accept();
        //driver.get(addressURL);

       // firstNameOfFirstAddress = driver.findElement(By.xpath("//table//tbody//tr//td[text()='"+text+"'"));
       // Assert.assertTrue(driver.findElements(By.xpath("//table//tbody//tr//td[text()='"+text+"'")).isEmpty());
    }

    @Test
    public void signOut(){
        WebElement signOutLink = driver.findElement(By.xpath("//a[text()='Sign out']"));
        signOutLink.click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://a.testaddressbook.com/sign_in");
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










}

