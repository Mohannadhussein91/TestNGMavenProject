
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

public class AmazonSearch {
	
	 @BeforeMethod
	   public void setup() {
			Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
		}
	 
	
	
  @Test (dataProvider = "searchItems")
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
  public String[] searchItems() {
	  String[] items = new String[6];
	  items[0]= "coffee mug";
	  items[1]= "pretty coffee mug";
	  items[2]= "ugly coffee mug";
	  items[3]= "gold coffee mug";
	  items[4]= "argentina coffee mug";
	  items[5]= "funny coffee mug";
	  
	 return  items;
  }
  
  
  @AfterMethod
  public void quitDriver() {
		Driver.quitDriver();
		
	}
}
