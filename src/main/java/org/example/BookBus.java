package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookBus {
    public static String busName="";
    public static  String busTime="";
    public static void sortWithRating(WebDriver driver) throws InterruptedException{
        Thread.sleep(1000);
        WebElement rating=driver.findElement(By.xpath("//div[@class='sorting-actions col']/div/a[3]"));
        rating.click();
    }

    public static void bookMostRated(WebDriver driver) throws  InterruptedException{
        List<WebElement> allBusTimings=driver.findElements(By.xpath("//span[@class='departure-time text-sm']"));
        List<WebElement> allBusname=driver.findElements(By.xpath("//div[@class='operator-info col s12 m5 l5']/h5"));
        List<WebElement> allBusCategory=driver.findElements(By.xpath("//div[@class='operator-info col s12 m5 l5']/p"));

        int busInd=-1;
        int itr=0;

        Boolean isAvailable=false;


        LocalTime thresholdTime = LocalTime.parse("21:00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<Integer> availableBusIndexes=new ArrayList<>();

        for(WebElement timeContainer:allBusTimings){
            String time=timeContainer.getText();
            LocalTime timeOrginal = LocalTime.parse(time, formatter);
            if (timeOrginal.isAfter(thresholdTime)) {
                availableBusIndexes.add(itr);
                isAvailable=true;
            }
            itr++;
        }
        //bus at night time available
        if(isAvailable){
            itr=0;
            for(Integer ind:availableBusIndexes){
                if(allBusCategory.get(ind).getText().equals("AC Sleeper (2 + 1)")){
                    busInd=ind;

                    break;
                }
            }

            BookBus.busName=allBusname.get(busInd).getText();
            BookBus.busTime=allBusTimings.get(busInd).getText();
            List<WebElement> selectSeatsBtn=driver.findElements(By.xpath("//button[@class='btn bus-info-btn filled primary sm inactive button']"));
            selectSeatsBtn.get(busInd).click();
            Thread.sleep(2000);
        }
        else{
            System.out.println("No available Buses at Night");
        }
    }
    public static void printAvailableBus(WebDriver driver){
        List<WebElement> allSeats=driver.findElements(By.xpath("//button[@class='seat sleeper']/span"));
        List<WebElement> seats=driver.findElements(By.xpath("//button[@class='seat sleeper']//*[name()='svg']//*[name()='rect'][1]"));

        //Printing Details
        System.out.println("Date of Travel : "+HomePage.Day+" "+HomePage.MontYear);
        System.out.println("Operator Name : "+BookBus.busName);
        System.out.println("Departure Time : "+BookBus.busTime);

        System.out.println("Available Seats");
        System.out.println("Upper Deck");
        for(int i=0;i<5;i++){
            WebElement seat1=seats.get(i);
            WebElement seat2=seats.get(i+5);
            if(seat1.getAttribute("fill").equals("white")&& seat2.getAttribute("fill").equals("white")){
                System.out.println(allSeats.get(i).getText()+" or "+allSeats.get(i+5).getText());
            }
        }
        System.out.println("Lower Deck");
        for(int i=16;i<21;i++){
            WebElement seat1=seats.get(i);
            WebElement seat2=seats.get(i+5);
            if(seat1.getAttribute("fill").equals("white")&& seat2.getAttribute("fill").equals("white")){
                System.out.println(allSeats.get(i).getText()+" or "+allSeats.get(i+5).getText());
            }
        }
        String cost=driver.findElement(By.xpath("//strong[@class='h5 fare']")).getText();
        System.out.println("Cost Starting At : "+cost);
    }


}
