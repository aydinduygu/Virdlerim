package com.codeforlite.virdlerim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class selenium {

    public static void main(String[] args) throws InterruptedException {


        System.setProperty("webdriver.chrome.driver","C:\\Users\\istanbul\\selenium\\ChromeDriver\\chromedriver.exe");
        WebDriver webDriver=new ChromeDriver();
        webDriver.get("https://www.google.com.tr");
        webDriver.manage().window().maximize();
        webDriver.findElement(By.className("gLFyf gsfi")).sendKeys("yasemin");
        Thread.sleep(2000);
        webDriver.findElement(By.className("gNO89b")).click();

    }
}
