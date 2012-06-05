package pl.edu.agh.iosr.brokers.intgration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogInLogOutIT {

	protected static final String USER = "user";
	protected static final String PASS = "password";
	private static final long TIMEOUT = 5000;

	protected static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}
	@Test
	public void testCorrectLogin() {
		login(USER, PASS);
		assertElemenVisible(By.className("stock-index-table"));
	}

	@Test
	public void testIncorrectUserName() {
		incorrectLoginAndVerify(USER + "fail", PASS);
	}

	@Test
	public void testIncorrectPassword() {
		incorrectLoginAndVerify(USER, PASS + "fail");
	}

	@Test
	public void testLogout() {
		login(USER,PASS);
		logout();
		assertElemenVisible(By.className("login-form"));		
	}

	
	private void incorrectLoginAndVerify(String user, String pass) {
		login(user, pass);
		assertElemenVisible(By.className("login-form"));		
		assertElemenVisible(By.className("warning"));
	}

	
	protected void logout() {
		driver.findElement(By.className("logout-link")).findElement(By.tagName("a")).click();
	}

	protected void login(String user, String pass) {
		driver.get("http://localhost:8080/login.htm");
		driver.findElement(By.name("j_username")).sendKeys(user);
		driver.findElement(By.name("j_password")).sendKeys(pass);
		driver.findElement(By.name("submit")).click();
	}

	protected void assertElemenVisible(final By by) {
		try {
			(new WebDriverWait(driver, 10))
				  .until(new ExpectedCondition<WebElement>(){
						@Override
						public WebElement apply(WebDriver d) {
							return d.findElement(by);
						}});
		} catch (TimeoutException e) {
			throw new RuntimeException("could not find " + by, e);
		}
	}

}
