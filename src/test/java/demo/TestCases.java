package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.flipkart.com/");
        
        Thread.sleep(5000);
        WebElement searchForProducts = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        Wrappers.sendKeys(searchForProducts, "Washing Machine");
        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        Wrappers.click(searchButton);

        // Wait for popularity filter to be clickable and click
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popularity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Popularity']")));
        Wrappers.click(popularity);

        // Wait for the search results to be present
        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='XQDdHH']")));
        int count = 0;

        for (int i = 0; i < searchResults.size(); i++) {
            try {
                // Re-locate the products to avoid StaleElementReferenceException
                searchResults = driver.findElements(By.xpath("//div[@class='XQDdHH']"));
                WebElement res = searchResults.get(i);
                String starRatingStr = res.getText();
                double starRatingDou = Double.parseDouble(starRatingStr);
                if (starRatingDou <= 4) {
                    count++;
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException caught. Re-locating the element.");
                searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='XQDdHH']")));
                i--; // Retry the same element index
            }
            // Thread.sleep(10000);
        }
        System.out.println("The count of products with less than or equal to 4 stars is: " + count);

        System.out.println("End Test case: testCase01");
    }

    @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Starting testCase02");
        driver.get("https://www.flipkart.com/");

        Thread.sleep(5000);
        WebElement closeLogin = driver.findElement(By.xpath("//span[@role='button']"));
        closeLogin.click();
        Thread.sleep(5000);
        // driver.findElement(By.xpath("//span[@role='button']")).click();
       WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title,'Search for Products')]"));
       Wrappers.sendKeys(searchBox, "iphone");
          WebElement searchhAppleVerification = driver.findElement(By.xpath("//input[contains(@title,'Search')]"));
       String applVerification = searchhAppleVerification.getAttribute("value");
       if (applVerification.equals("iphone")) {
        System.out.println("Verified iphone is Entered in");
       }

       WebElement searchClick = driver.findElement(By.xpath("//button[contains(@aria-label,'Search')]"));
       Wrappers.click(searchClick);
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='yKfJKb row']")));

    

       List<WebElement> productCards = driver.findElements(By.xpath("//div[@class='tUxRFH']"));
       for(WebElement cards : productCards){
        try {
            WebElement titleOfProduct = cards.findElement(By.xpath("//div[@class='KzDlHZ']"));
            String title = titleOfProduct.getText();
            WebElement discountElement = cards.findElement(By.xpath("//div[@class='UkUFwK']"));
            String discountText = discountElement.getText();
            String discountValueStr = discountText.replaceAll("[^0-9]", "");
            int discountValue = Integer.parseInt(discountValueStr.trim());
            if(discountValue>17){
                System.out.println("title ="+title);
                System.out.println("Discount percent="+discountValue);
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("..");
        }
       }

       System.out.println("End testCase02");
    }


    @Test
    public void testCase05(){
        System.out.println("Starting the testCase 05 ");
        driver.get("https://crio-qkart-frontend-qa.vercel.app/");
        WebElement login = driver.findElement(By.xpath("//button[text()='Login']"));
        login.click();
        WebElement userName = driver.findElement(By.xpath("//input[@placeholder='Enter Username']"));
        userName.sendKeys("null");
        
    }





        // WebElement searchForProducts = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        // sendKeys(searchForProducts, "iPhone");
        // WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        // click(searchButton);

        // // Get the list of product titles
        // List<WebElement> iphoneResultsTitle = driver.findElements(By.xpath("//div[@class='KzDlHZ']"));
        // for (int i = 0; i < iphoneResultsTitle.size(); i++) {
        //     WebElement res = iphoneResultsTitle.get(i);
        //     System.out.println(res.getText());
        // }

        // // Get the list of discount percentages
        // List<WebElement> discountPercentage = driver.findElements(By.xpath("//div[@class='UkUFwK']"));
        // for (int i = 0; i < discountPercentage.size(); i++) {
        //     WebElement disc = discountPercentage.get(i);
        //     String discStr = disc.getText();
        //     String discValStr = discStr.replaceAll("[^0-9]", "");
        //     int discValue = Integer.parseInt(discValStr.trim());
        //     if (discValue > 17 && discValue<50) {
        //         WebElement res = 
        //         System.out.println("Discount: " + discStr);
        //     }
        // }

    //     System.out.println("End testCase02");
    // }

    // public static void main(String[] args) {
    //     TestCases test = new TestCases();
    //     try {
    //         test.testCase01();
    //         test.testCase02();
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     } finally {
    //         test.endTest();
    //     }
    // }



    // Search “Coffee Mug”, select 4 stars and above, and print the Title and image URL of the 5 items with highest number of reviews

    @Test
    public void testCase03() throws InterruptedException{
        System.out.println("Start testcase 03");
        driver.get("https://www.flipkart.com/");
        Thread.sleep(2000);
        WebElement searchForProducts = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        Wrappers.sendKeys(searchForProducts, "Coffee Mug");
        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        Wrappers.click(searchButton);
        WebElement  fourStarAndAbove = driver.findElement(By.xpath("(//input[@class='vn9L2C'])[2]"));
        if(!fourStarAndAbove.isSelected()){
            Wrappers.click(fourStarAndAbove);
            System.out.println("clicked on 4star and above");
        }
        List<WebElement> reviews = driver.findElements(By.xpath("//span[@class='Wphh3N']"));
        for(int i = 0 ; i<reviews.size();i++){
            WebElement rev = reviews.get(i);
            String reviewString  = rev.getText();
            // System.out.println(reviewString);
            String revNum = reviewString.replaceAll("[^0-9]", "");
           int  revNumInt = Integer.parseInt(revNum);
           System.out.println(revNumInt);
           

        }
    }


    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}