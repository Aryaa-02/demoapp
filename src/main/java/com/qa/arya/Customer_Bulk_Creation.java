package com.qa.arya;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class Customer_Bulk_Creation {

	public static void main(String[] args) throws InterruptedException {
		// Login in Shopify Store
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.shopify.com/in");
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.findElement(By.xpath("//a[@class='whitespace-nowrap hover:underline text-white']")).click();
		driver.findElement(By.id("account_email")).sendKeys("Enter your Email id");
		WebElement emailbutton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='ui-button__text']")));
		emailbutton.submit();
		WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("account_password")));
		password.sendKeys("Enter your Password");
		WebElement login = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='ui-button__text']")));
		login.submit();

		Faker faker = new Faker();
		// Creating bulk Shopify Customer using for loop
		try {
			wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("(//span[@class='Polaris-Navigation__Text'])[4]")))
					.click();
		} catch (StaleElementReferenceException e) {
			wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("(//span[@class='Polaris-Navigation__Text'])[4]")))
					.click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Add customer']")))
				.click();

		for (int i = 1; i >= 5; i++) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='firstName']")))
					.sendKeys(faker.name().firstName());
			driver.findElement(By.name("lastName")).sendKeys(faker.name().lastName());
			driver.findElement(By.name("email")).sendKeys(faker.internet().emailAddress());
			// I have tried to use the local here but it's not working as expected.
			Random random1 = new Random();
			long phone1 = (long) (Math.pow(10, 7) + random1.nextInt((int) Math.pow(10, 7)));
			driver.findElement(By.name("phone")).sendKeys("+9191" + phone1);
			WebElement test1 = driver.findElement(By.xpath("//input[@name='customer[company]']"));
			Actions actions1 = new Actions(driver);// Inserting action here because we have to scroll down to locate the
													// element
			actions1.scrollToElement(test1).build().perform();
			test1.sendKeys(faker.company().name());
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Save']"))).click();
			// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(@class,
			// 'Polaris-Text--root') and text()='Customer created']")));
			// Currently adding the Thread.Sleep here i need to find the best condition so
			// that it will wait for the page to fully reload in java.
			Thread.sleep(20000);
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//span[contains(@class, 'Polaris-Text--root') and text()='Customers']"))).click();
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Add customer']")))
					.click();
			System.out.println(1);
		}
	}
}