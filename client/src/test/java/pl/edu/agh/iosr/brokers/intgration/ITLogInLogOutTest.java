package pl.edu.agh.iosr.brokers.intgration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ITLogInLogOutTest {

	private static final String USER = "user";
	private static final String PASS = "password";
	private static final long TIMEOUT = 2000;

	WebDriver driver;

	@Before
	public void setUp() {
		driver = new HtmlUnitDriver();
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

	protected void assertElemenVisible(By by) {
		long end = System.currentTimeMillis() + TIMEOUT;
		while (System.currentTimeMillis() < end) {
			WebElement resultsDiv = driver.findElement(by);
			if (resultsDiv.isDisplayed()) {
				break;
			}
		}
		assertTrue("After " + TIMEOUT + "ms could not find element: " + by,
				System.currentTimeMillis() < end);
	}

}
