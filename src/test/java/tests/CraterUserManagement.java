package tests;

import org.testng.annotations.Test;

import pages.CraterDashboardPage;
import pages.CraterLoginPage;
import utils.BrowserUtils;
import utils.Driver;
import utils.TestDataReader;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class CraterUserManagement {
	
	BrowserUtils utils;
  @Test
  public void validLogin() throws InterruptedException {
	  /*
	    Scenario: Successful log in 
	    Given user is on the login page
	    When user enters valid user name and password
	    And click login Button 
	    Then user should be on the dash board page
	   */
	  Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
	  CraterLoginPage loginpage = new CraterLoginPage();
	  Thread.sleep(1000);
	  loginpage.login();
	  //this way for chrome to fix the issue with sending key problem by using the Action instead
//	  utils = new BrowserUtils();
//	  utils.sendKeysWithActionsClass(loginpage.useremail,TestDataReader.getProperty("craterValidUserEmail"));
//	 // loginpage.useremail.sendKeys(TestDataReader.getProperty("craterValidUserEmail"));
//	  Thread.sleep(1000);
//	  utils.sendKeysWithActionsClass(loginpage.password,TestDataReader.getProperty("craterValidPassword"));
//	 // loginpage.password.sendKeys(TestDataReader.getProperty("craterValidPassword"));
//	  loginpage.loginButton.click();
	  
	  CraterDashboardPage dashboard = new CraterDashboardPage();
	  //this is the first way to verify we are on the dash board page
	  Assert.assertTrue(dashboard.amountDueText.isDisplayed());
	  // second way to verify is by url contains dashboard
	  String dashboardUrl = Driver.getDriver().getCurrentUrl();
	  Assert.assertTrue(dashboardUrl.contains("dashboard"));
	  
	  
  }
  
  @Test (dataProvider = "credential")
  public void invalidLogin(String useremail, String password) throws InterruptedException {
	  /*
	   Scenario: Invalid login attempts
	   Given user is on the login page 
	   when user enters invalid user name and password 
	   and click login button
	   then error message appears 
	   and user not log in 
	   */
	  Driver.getDriver().get(TestDataReader.getProperty("craterUrl"));
	  CraterLoginPage loginpage = new CraterLoginPage();
	  Thread.sleep(1000);
	  BrowserUtils utils = new BrowserUtils();
	  
	  
	  if (useremail.isBlank() || password.isBlank()) {
	  loginpage.useremail.sendKeys(useremail);
	  loginpage.password.sendKeys(password);
	  loginpage.loginButton.click();
	  utils.waitUntilElementVisible(loginpage.fieldRequired);
	  Assert.assertTrue(loginpage.fieldRequired.isDisplayed());
	  
	  }else {
		  loginpage.useremail.sendKeys(useremail);
		  loginpage.password.sendKeys(password);
		  loginpage.loginButton.click();
		  utils.waitUntilElementVisible(loginpage.invalidUserErrorMessage);
		  Assert.assertTrue(loginpage.invalidUserErrorMessage.isDisplayed());
		  // just to verify we are still on the login page 
		  // we can do that either by the login button or by assert the current url
		  Assert.assertTrue(loginpage.loginButton.isDisplayed());
		  
	  }
	  
	  
  }
  
  @DataProvider
  public String[][] credential() {
	  String[][] names = new String[4][2];
	  names[0][0] = "husseinmohanad@gmail.com";
	  names[0][1] =  "Wrongpassword";
	  
	  names[1][0] = "wrongemail@gkmfd.com";
	  names[1][1] =  "Password1234";
	  
	  names[2][0] = "";
	  names[2][1] =  "Password1234";
	  
	  names[3][0] = "wrongemail@gkmfd.com";
	  names[3][1] =  "";
	  
	  
	  return names;
	  
	  
  }
  
  
  
  @BeforeMethod
  public void setUp() {
	  Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	
  }
  
  
  

  @AfterMethod
  public void tearDown() {
	  Driver.quitDriver();
  }

}
