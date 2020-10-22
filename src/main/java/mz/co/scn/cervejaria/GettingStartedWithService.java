
package mz.co.scn.cervejaria;
/**
 *
 * @author Sidónio Goenha on Jul 15, 2020
 *
 */

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GettingStartedWithService {

	private static ChromeDriverService service;
	private WebDriver driver;

	@BeforeClass
	public static void createAndStartService() throws IOException {
		service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("E:/Programs/chromedriver_win32/chromedriver")).usingAnyFreePort()
				.build();
		service.start();

	}
	
	@AfterClass
	public static void stopService() {
		service.stop();
	}
	
	@Before
	public void createDriver( ) {
		driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
	}
	
	@After
	public void quitDriver() {
		driver.quit();
	}
	
	@Test
	public void testGoogleSearch() throws InterruptedException {
		driver.get("https://www.google.com");
		Thread.sleep(5000);  // Let the user actually see something!
	    WebElement searchBox = driver.findElement(By.name("q"));
	    searchBox.sendKeys("ChromeDriver");
	    searchBox.submit();
	    Thread.sleep(5000);  // Let the user actually see something!
	    driver.quit();
	}

}
