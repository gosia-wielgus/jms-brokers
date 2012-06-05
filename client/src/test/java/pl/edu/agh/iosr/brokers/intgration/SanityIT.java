package pl.edu.agh.iosr.brokers.intgration;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SanityIT {
	static WebDriver driver;
	
	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
	}
	
	@AfterClass
	public static void tearDown() {
		driver.quit();	
	}
	
	@Test
	public void testLoginPageDisplayed(){
		driver.get("http://localhost:8080");
		assertEquals("Log in", driver.getTitle());
	}
	
}
