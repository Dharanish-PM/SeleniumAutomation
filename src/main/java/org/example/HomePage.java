package org.example;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    public static String MontYear="";
    public static String Day="";

    public static void inputSourceAndDestination(WebDriver driver,String src, String dest) throws  InterruptedException{

        WebElement source= driver.findElement(By.xpath("//input[@placeholder='From Station']"));
        WebElement destination=driver.findElement(By.xpath("//input[@placeholder='To Station']"));
        HomePage.waitForEnableCondition(driver,source);
        HomePage.waitForEnableCondition(driver,destination);
        source.click();
        source.sendKeys(src);

        WebElement dropDownSrc=driver.findElement(By.xpath("//ul[@class='collection auto-complete-list primary sm false']/li"));
        Assert.assertTrue(dropDownSrc.isDisplayed());
        Thread.sleep(2000);
        dropDownSrc.click();

        destination.click();
        destination.sendKeys(dest);
        WebElement dropDownDest=driver.findElement(By.xpath("//ul[@class='collection auto-complete-list primary sm false']/li"));
        Assert.assertTrue(dropDownDest.isDisplayed());
        Thread.sleep(1000);
        dropDownDest.click();

    }

    public static void selectDate(WebDriver driver,String MonthYr,String day)throws InterruptedException{

        WebElement calender=driver.findElement(By.xpath("//div[@class='container date-input ']"));
        calender.click();
        Thread.sleep(1000);

        while(!driver.findElement(By.xpath("//div[@class='container month ']/div[2]")).getText().equals(MonthYr)){
            WebElement nextMonth=driver.findElement(By.xpath("//div[@class='container month ']/div[3]"));
            nextMonth.click();
        }

        HomePage.MontYear=MonthYr;
        HomePage.Day=day;

        List<WebElement> selectDay= driver.findElements(By.xpath("//div[@class='container date ']//span[@tabindex='1']"));
        for(WebElement dayContainer:selectDay){
            if(dayContainer.getText().equals(day)){
                dayContainer.click();
                break;
            }

        }
    }

    public static void submitDetails(WebDriver driver){
        WebElement submitBtn=driver.findElement(By.xpath("//a[@class='btn btn-search filled primary lg inactive button']"));
        submitBtn.click();
    }


    public static void waitForEnableCondition(WebDriver driver,WebElement e){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(e));
    }
}
