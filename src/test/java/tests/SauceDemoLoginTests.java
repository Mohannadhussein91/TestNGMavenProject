package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.SauceDemoPage;
import utils.Driver;

public class SauceDemoLoginTests {
	
	@Test (dataProvider = "credential")
	public void credtialTest(String username, String password) {
		Driver.getDriver().get("https://saucedemo.com");
		
		SauceDemoPage page = new SauceDemoPage();
		page.username.sendKeys(username);
		page.password.sendKeys(password);
		page.loginButton.click();
		Assert.assertTrue(page.errorMessage.getText().contains("Username and password do not match"));
		
	}
  @DataProvider
  public String[][] credential() {
	  String[][] names = new String[2][2];
	  names[0][0] = "standard_user";
	  names[0][1] =  "standardfdfds";
	  
	  names[1][0] = "standard_user11";
	  names[1][1] =  "secret_sauce";
	  return names;
	  
	  // row 0 | 0 | 1 | 
	  // row 1 | 0 | 1 |
  }
}
