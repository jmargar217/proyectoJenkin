package com.example.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.example.demo.model.Usuario;

class SeleniumTest {
	
	private WebDriver driver;
	
	@BeforeEach
	void init() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
		ChromeOptions options = new ChromeOptions();
		
		
		driver = new ChromeDriver(options);
	}
	
	@Test
	public void Home() throws InterruptedException {
		driver.get("http://localhost:9000");
		
		WebElement user = driver.findElement(By.id("user"));
		
		WebElement password = driver.findElement(By.id("password"));
		
		WebElement submit = driver.findElement(By.id("submit"));
		
		Thread.sleep(4000);
		user.sendKeys("jmargar217");
		Thread.sleep(4000);
		password.sendKeys("4959");
		submit.click();
		
		String titulo = driver.getCurrentUrl();
		assertTrue(titulo.equals("http://localhost:9000/login/submit"));
		
	}

	

}
