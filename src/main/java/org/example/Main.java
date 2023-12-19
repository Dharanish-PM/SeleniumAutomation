package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.print.Book;

public class Main {
    public static void main(String[] args) {
        ChromeDriver driver=new ChromeDriver();
        driver.get("https://www.abhibus.com/");
        try{
            Thread.sleep(1000);
            HomePage.inputSourceAndDestination(driver,"Coimbatore","Bangalore");
            HomePage.selectDate(driver,"February 2024","10");
            HomePage.submitDetails(driver);

            BookBus.sortWithRating(driver);
            Thread.sleep(1000);
            BookBus.bookMostRated(driver);
            BookBus.printAvailableBus(driver);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
