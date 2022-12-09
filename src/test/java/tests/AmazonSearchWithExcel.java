package tests;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.AmazonPage;
import utils.Driver;
import utils.ExcelUtils;

public class AmazonSearchWithExcel {
	
	 @BeforeMethod
	   public void setup() {
			Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
		}
	 @AfterMethod
	  public void quitDriver() {
			Driver.quitDriver();
			
		}
	 
	 @Test
	 public void test() {
		 ExcelUtils.openExcelFile("./src/test/resources/testData/searchItems.xlsx", "Sheet1");
		 System.out.println(ExcelUtils.getUsedRowsCount());
		 System.out.println(ExcelUtils.getCellData(1,0));
	 }
	 
	 
	
    @Test (dataProvider = "searchItem")
    public void AmazonSearchTest(String item) {
	  AmazonPage amazonpage = new AmazonPage();
	  Driver.getDriver().get("https://amazon.com");
	  amazonpage.searchBox.sendKeys(item);
	  amazonpage.searchBtn.click();
	  String actualText = amazonpage.searchedItemText.getText().substring(1, item.length()+1);
	  System.out.println(actualText);
	  Assert.assertEquals(actualText, item);
}

  
    @DataProvider
     public String[] searchItem() {
	   String[] items = ExcelUtils.getExcelDataInAColumn("./src/test/resources/testData/searchItems.xlsx", "Sheet1");
	   return items;
   }


}
