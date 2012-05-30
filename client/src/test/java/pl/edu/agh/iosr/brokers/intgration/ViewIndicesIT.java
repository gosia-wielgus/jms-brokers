package pl.edu.agh.iosr.brokers.intgration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ViewIndicesIT extends LogInLogOutIT {

	@Test
	public void testViewIndices() {
		login(USER, PASS);
		By anyCellBy = By.xpath("//table[@class='stock-index-table']//td");
		assertElemenVisible(anyCellBy);
		List<WebElement> rows = driver.findElement(By.className("stock-index-table")).findElements(By.tagName("tr"));
		for (int i=1; i<rows.size(); i++) {
			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			assertEquals(4, cells.size());
			assertTrue(Double.valueOf(cells.get(1).getText()) > 0);
			assertTrue(Double.valueOf(cells.get(2).getText()) > 0);
			assertTrue(cells.get(3).getText().contains("%"));
		}
	}

}
