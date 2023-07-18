package herokupp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Driver;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class herokupTesting {

	
	@Test(priority=1, enabled=true)
	public static void CheckBrokenImage() throws InterruptedException, IOException {
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		WebDriver driver = new ChromeDriver();
		String url1 = "http://the-internet.herokuapp.com/broken_images";
		
		driver.get(url1);
		Thread.sleep(3000);
		
		//TO GO INTO THE OUTER DIV WHERE ALL IMAGES ARE PRESENT
		WebElement allImageColl = driver.findElement(By.xpath("//div[@class=\"example\"]"));
		
		//TO GET ALL IMAGE 
		List<WebElement> All_Images = allImageColl.findElements(By.tagName("img"));
		
		//TO GET THE SIZE OF IMAGE COLLECTION
		int size = All_Images.size();
		System.out.println("size"+size);
		
		//FOR ITERATION OVER EACH IMAGE TO KNOW WHICH ONE IS BROKEN OR NOT 
		for(WebElement image:All_Images) {
			String imgSrc = image.getAttribute("src");
			
			URL url = new URL(imgSrc);
			URLConnection urlConnect = url.openConnection();
			HttpURLConnection httpToConnect = (HttpURLConnection)urlConnect;
			httpToConnect.setConnectTimeout(5000);
			httpToConnect.connect();
			
			int statusCode = httpToConnect.getResponseCode();
			String statusMessage = httpToConnect.getResponseMessage();
			
			//CONDITION BASED ITERATION
			if(httpToConnect.getResponseCode() == 200) {
				System.out.println(imgSrc+ "/" +statusCode + "/"+  statusMessage+"/" +"PASSED");
			}else {
				System.out.println(imgSrc+ "/" +statusCode + "/"+  statusMessage+"/" +"FAIL");
			}
					
			
		}
		driver.quit();
		
	}
	@Test(priority=2, enabled=true)
	public static void uploadFile() throws InterruptedException {
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		WebDriver driver = new ChromeDriver();
		String url2 = "http://the-internet.herokuapp.com/upload";
		
		driver.get(url2);
		Thread.sleep(2000);
		//TO UPLOAD FILE
		driver.findElement(By.xpath("//input[@id=\"file-upload\"]")).sendKeys("C:\\new job\\my pic final.jpg");
		Thread.sleep(2000);
		
		//TO CLICK ON UPLOAD BUTTON
		driver.findElement(By.xpath("//input[@id=\"file-submit\"]")).click();
		Thread.sleep(3000);
		
		//FOR ASSERTION WHEN FILE UPLOADED
		String actTitle = driver.findElement(By.xpath("//h3[text()=\"File Uploaded!\"]")).getText();
		String expTitle = "File Uploaded!";
		AssertJUnit.assertEquals(actTitle, expTitle);
		
		driver.quit();
		
		
	}

}
