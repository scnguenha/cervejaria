
package mz.co.scn.cervejaria;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Sidónio Goenha on Jul 14, 2020
 *
 */
public class SeleniumTest {
	@Test
	public void testGoogleSearch() throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.google.com/");
		Thread.sleep(5000);
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("ChromeDriver");
		searchBox.submit();
		Thread.sleep(5000);
		driver.quit();
	}
}
