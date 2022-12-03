package org.amazonportal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MobileOrder {
	@DataProvider (name ="credential")
	public Object [][] loginCreds(){
		return new Object[][] {{"ramesh","pass"}};
	}
	@DataProvider (name ="mobiles")
	public Object [][] input(){
		return new Object[][] {{"one plus mobiles"}};
	}
	static WebDriver driver;
	@BeforeClass
	public static void browserlaunch() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		//System.out.println("To launch");
	}
	@AfterClass
	public static void browserclose() {
		//System.out.println("To closed");
		//driver.quit();
	}
	@BeforeMethod
	public void launchstTime() {
		//System.out.println("finish time");
	}
	@AfterMethod
	public void launchendTime() {
		//System.out.println("finish time");
	}
	@Test(priority = 1)
	public void launch() {
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
	}
	@Test (priority = 2 , dataProvider = "mobiles" )
	public void search (String name) throws InterruptedException {
	Thread.sleep(3000);
	driver.findElement(By.xpath("//input[@type='text']")).sendKeys(name);
	driver.findElement(By.xpath("//input[@type='submit']")).click();
	}
	@Test (priority = 3)
	public void selectMobile() throws InterruptedException, IOException {
	Thread.sleep(3000);
	File f = new File("C:\\Users\\Windows\\Ramesh\\TestNG\\TestNG.xlsx");
	FileOutputStream  f1 = new FileOutputStream(f);
	Workbook w = new XSSFWorkbook();
	Sheet s = w.createSheet("Trace");
	List<WebElement> trace =driver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
	for(int i=0;i<trace.size();i++) {
		WebElement name =trace.get(i);
		String text=name.getText();
		Row r=s.createRow(i);
		Cell c = r.createCell(0);
		c.setCellValue(text);
		System.out.println(text);
	}
		w.write(f1);	
	}
		
	@Test (priority = 4)
	public void windowHandle() throws InterruptedException {
	Thread.sleep(3000);
	String parent = driver.getWindowHandle();
	WebElement one = driver.findElement(By.xpath("(//span[@class='a-size-medium a-color-base a-text-normal'])[5]"));
	one.click();
	Set<String>child =driver.getWindowHandles();
	for(String x:child) {
		if(!parent.equals(child)) {
			driver.switchTo().window(x);
			System.out.println(x);
		}
	}
}
	@Test (priority = 5)
	public void exceloperation() throws IOException, InterruptedException {
		Thread.sleep(3000);
		String childtext = driver.findElement(By.xpath("//span[@id='productTitle']")).getText();
		System.out.println(childtext);
		File f = new File("C:\\Users\\Windows\\Ramesh\\TestNG\\TestNG.xlsx");
		FileInputStream  f1 = new FileInputStream(f);
		Workbook w = new XSSFWorkbook(f1);
		Sheet s = w.getSheet("Trace");
		Row r = s.getRow(0);
		Cell c = r.getCell(0);
		String CellValue = c.getStringCellValue();
		SoftAssert sa = new SoftAssert();
       sa.assertEquals(childtext, CellValue);
     //	Assert.assertEquals(childtext, CellValue);
	    }
		@Test (priority = 7)
		public void refresh() {
		driver.navigate().refresh();
		}
	    @Test (priority = 6)
	    public void addTocart() throws InterruptedException {
 		JavascriptExecutor js= (JavascriptExecutor) driver ;
		Thread.sleep(3000);
		WebElement down = driver.findElement(By.xpath("//span[contains(text(),'Secure transaction')]"));
		js.executeScript("window.scrollBy(0,500)", down);
		driver.findElement(By.xpath("//input[@title='Add to Shopping Cart']")).click();
		}
}
