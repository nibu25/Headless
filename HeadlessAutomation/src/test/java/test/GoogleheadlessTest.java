package test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GoogleheadlessTest {
	ChromeOptions options;
	// Set the window size

	// Initialize WebDriver with ChromeOptions
	WebDriver driver;

	// Perform actions
	@BeforeTest
	public void beforeTest() {
		options = new ChromeOptions();
		options.addArguments("--headless"); // Run in headless mode
		options.addArguments("--disable-gpu"); // Disable GPU (for compatibility)
		options.addArguments("--window-size=1920,1080");
		driver = new ChromeDriver(options);

	}

	@Test
	public void f() {
		driver.get("https://www.google.co.in/");
		System.out.println("Page Title: " + driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Google");
	}

	@AfterMethod
	public void takeScreenshotOnTestFailure(org.testng.ITestResult result) throws IOException {
		if (result.getStatus() == org.testng.ITestResult.FAILURE) {
			// Take a screenshot on failure
			captureScreenshot("failure_" + result.getMethod().getMethodName());
		} else if (result.getStatus() == org.testng.ITestResult.SUCCESS) {
			// Take a screenshot on success
			captureScreenshot("success_" + result.getMethod().getMethodName());
		}
	}

	// Method to capture screenshot
	private void captureScreenshot(String screenshotName) throws IOException {
		// Capture screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Save screenshot to desired location
		FileUtils.copyFile(screenshot, new File(screenshotName + ".png"));
		
		System.out.println(screenshotName + " screenshot taken.");
	}

	// Close the browser after tests
	@AfterTest
	public void afterTest() {
		driver.quit();
	}
}
