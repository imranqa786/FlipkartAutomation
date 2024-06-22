package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    static ChromeDriver driver;
     static void scrollToElem(WebElement elementToView) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", elementToView);
    }

    public static void sendKeys(WebElement elem, String toSend) {
        try {
            if (elem.isDisplayed()) {
                elem.clear();
                elem.sendKeys(toSend);
            }
        } catch (Exception e) {
            System.out.println("Element name=" + elem.getText() + " is Not displayed");
        }
    }


    public static void click(WebElement elementToClick) {
        try {
            if (elementToClick.isDisplayed()) {
                elementToClick.click();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
