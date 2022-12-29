package tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CraterCommonPage;
import pages.CraterItemsPage;
import pages.CraterLoginPage;
import utils.BrowserUtils;
import utils.DButils;
import utils.Driver;
import utils.TestDataReader;

public class CarterItemsTests {
	
	CraterCommonPage commonpage;
	CraterItemsPage itemsPage;
	BrowserUtils utils;
	CraterLoginPage loginPage;
	DButils dbutils;
	
	String newItemName = "Argentina Jersy";
  
  
  @Test
	public void createItem() {
		/*
		 Create an item on UI.
         Then go to database, and query from the items table using select * to get the information
  	     Then verify the information that you have provided on UI is correct. 
  	     Then update your Item on the UI, come back to database and verify the update is in effect.
         Then delete the Item on the UI,  come back to database and verify the estimate no longer exist. 
		 */
	  
	  commonpage = new CraterCommonPage();
	  itemsPage  = new CraterItemsPage();
	  utils      = new BrowserUtils();
	  
		
	  	
		Assert.assertTrue(commonpage.commonPageItemsLink.isDisplayed());
		// click on the items tab
		commonpage.commonPageItemsLink.click();
		// verify that user is on the Add Items page
		Assert.assertTrue(itemsPage.addItemButton.isDisplayed());
		// click on the Add Item button
		itemsPage.addItemButton.click();
		// verify that the add item modal displays
		Assert.assertTrue(itemsPage.newItemHeaderText.isDisplayed());
		// provide item information
		newItemName = newItemName + utils.randomNumber();
		itemsPage.addItemNameField.sendKeys(newItemName);
		itemsPage.addItemPriceField.sendKeys("149.99");
		itemsPage.addItemUnitField.click();
		// wait until the pc unit is display
		utils.waitUntilElementVisible(itemsPage.pc_unit);
		// click on the pc unit
		itemsPage.pc_unit.click();
		
		itemsPage.addItemDescription.sendKeys("World cup champions!");
		itemsPage.saveItemButton.click();
		
		//wait until the item create success message displays
		utils.waitUntilElementVisible(itemsPage.itemCreateSuccessMessage);
		Assert.assertTrue(itemsPage.itemCreateSuccessMessage.isDisplayed());
		
		WebElement newItem = Driver.getDriver().findElement(By.xpath("//a[text()='"+newItemName+"']"));
		Assert.assertTrue(newItem.isDisplayed());
		
		
		// go to the database and select the item you just created
		System.out.println("New Argentina Jersy with Number is: " + newItemName);
		dbutils = new DButils();
		String query = "Select * From items Where name='"+newItemName+"'";
	    List<String> itemsData = dbutils.selectArecord(query);
	    System.out.println("Item Id: " + itemsData.get(0));
	    System.out.println("Item Name:" + itemsData.get(1));
	    
	    Assert.assertEquals(itemsData.get(1), newItemName);
	    
	    //Then update your item on the UI.
	    //come back to database and verify the update is in effect.
	    
	    Driver.getDriver().findElement(By.xpath("//a[text()='"+newItemName+"']")).click();
	    //verify that user on the edit item page
	    Assert.assertTrue(itemsPage.editItemHeaderText.isDisplayed());
	    //edit item description
	    String itemNewDescription = "We Are The Champions of the world!";
	    //first clear the existing text
	    utils.clearTextOfAFieldMac(itemsPage.addItemDescription);
	    //update with the updated text
	    itemsPage.addItemDescription.sendKeys(itemNewDescription);
	    //click on update item button
	    itemsPage.updateItemButton.click();
	    utils.waitUntilElementVisible(itemsPage.itemUpdatedSuccessMessage);
	    //verify the message banner displays
	    Assert.assertTrue(itemsPage.itemUpdatedSuccessMessage.isDisplayed());
	    
	    // Select query to get the item data from the DB
	    String updatequery = "Select * From items Where name='"+newItemName+"'";
	    List<String> updatedData = dbutils.selectArecord(query);
	    System.out.println("Item Id: " + updatedData.get(0));
	    System.out.println("Item Name:" + updatedData.get(1));
	    System.out.println("Item Description: " + updatedData.get(2));
	    //verify that the updated description match the expected
	    Assert.assertEquals(updatedData.get(2), itemNewDescription);
	    
	    //Then delete the Item on the UI
	    // come back to database and verify the item no longer exist.
 Driver.getDriver().findElement(
		By.xpath("(//a[text()='"+newItemName+"']//parent::td)//following-sibling::td[4]//button")).click();
        
        //click on the delete button
		utils.waitUntilElementVisible(itemsPage.deleteItemButton);
		itemsPage.deleteItemButton.click();
		// wait for the Ok button to be visible
		utils.waitUntilElementVisible(itemsPage.deleteOKButton);
		// click on Ok button
		itemsPage.deleteOKButton.click();
		
		// wait and verify the delete success message display
		utils.waitUntilElementVisible(itemsPage.itemDeletedSuccessMessage);
		Assert.assertTrue(itemsPage.itemDeletedSuccessMessage.isDisplayed());
		
		
		// data base query and verify the list is empty
		String deletedQuery = "SELECT * FROM items WHERE name='"+newItemName+"';";
	    List<String> deletedData = dbutils.selectArecord(deletedQuery);
		Assert.assertTrue(deletedData.isEmpty());
		//either one of the assertion works
		Assert.assertTrue(deletedData.size() == 0);
 
 
	    
	    
	    
	}

  
  
  
  @BeforeMethod
  public void setUp() throws InterruptedException {
	  loginPage = new CraterLoginPage();
	  Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
	  Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  loginPage.login();
	  Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }
  
  
 
  @AfterMethod
  public void wrapup() {
	  Driver.quitDriver();
  }
}
