import TestingAddressBookSuite.MainPageTest;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        MainPageTestExecutor();
        /*ChromeDriver driver = initializeDriverWithSettings();
        testMainPage(driver);*/


    }

    public static void MainPageTestExecutor() throws InterruptedException {
        MainPageTest mainPageTest = new MainPageTest();
        mainPageTest.testMainPageTitle();
        mainPageTest.testMainPageHeaders();
        mainPageTest.clickHomeButton();
    }


    /*public static ChromeDriver initializeDriverWithSettings(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        return new ChromeDriver(options);
    }

    public static void testMainPage(ChromeDriver driver){
        driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
        driver.get("http://a.testaddressbook.com/");

        WebElement mainHeader = driver.findElement(By.xpath("//div[@class='text-center']//child::h1"));
        new WebDriverWait(driver,3).until(ExpectedConditions.visibilityOf(mainHeader));

        String expectedTitle = "Address Book";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle,actualTitle);

        String expectedMainHeader = "Welcome to Address Book";
        Assert.assertEquals(expectedMainHeader,mainHeader.getText());

        String expectedSubHeader = "A simple web app for showing off your testing";
        WebElement subHeader = driver.findElement(By.xpath("//div[@class='text-center']//descendant::h4"));
        Assert.assertEquals(expectedSubHeader,subHeader.getText());

    }*/
}
