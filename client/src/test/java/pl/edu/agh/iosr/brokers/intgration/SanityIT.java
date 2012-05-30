package pl.edu.agh.iosr.brokers.intgration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SanityIT {

	@Test
	public void testLoginPageDisplayed(){
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080");
		assertEquals("Log in", driver.getTitle());
	}
	
}
