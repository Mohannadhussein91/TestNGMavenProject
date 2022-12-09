package tests;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CommonPage;
import pages.SignUpPage;
import utils.Driver;
import utils.TestDataReader;

public class SignupTests {
   @BeforeMethod (groups = "smoke-test")
   public void setup() {
		Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
   
   @Test
   public void test2() {
	   System.out.println("Test 2" + "thread:" +Thread.currentThread().getId());
   }
   @Test
   public void test3(){
	   
	   System.out.println("Test 3" + "thread:"+Thread.currentThread().getId());
   }
   
   @Test
   public void test4() {
	   
	   System.out.println("Test 4"+ "thread:"+Thread.currentThread().getId());
   }
	
	
  @Test (groups = {"smoke-test", "signupPage"}, description = "verify signup page components")
  public void signUpPage(){
	  CommonPage commonpage = new CommonPage();
	  SignUpPage signuppage = new SignUpPage();
	  //when
	  Driver.getDriver().get(TestDataReader.getProperty("appUrl"));
	  //and click
	  Assert.assertTrue(commonpage.welcomeLink.isDisplayed());
	  commonpage.welcomeLink.click();
	  //and click 
	  Assert.assertTrue(commonpage.signUpButton.isDisplayed());
	  commonpage.signUpButton.click();
	  
	  //verify the sign up page web components
	  
	  Assert.assertTrue(signuppage.signupText.isDisplayed());
	  
	  //email field verification
	  Assert.assertTrue(signuppage.emailFieldLabel.isDisplayed());
	  Assert.assertEquals(signuppage.emailField.getAttribute("placeholder"), "Please Enter Your Email");
	  
	  
	  //first name field verfication 
	  Assert.assertTrue(signuppage.firstNameLabel.isDisplayed());
	  Assert.assertEquals(signuppage.firstNameField.getAttribute("placeholder"), "Please Enter Your First Name");
	  
	  //last name field verfication 
	  Assert.assertTrue(signuppage.lastNameLabel.isDisplayed());
	  Assert.assertEquals(signuppage.lastNameField.getAttribute("placeholder"), "Please Enter Your Last Name");

	  //password field verfication
	  Assert.assertTrue(signuppage.passwordLabel.isDisplayed());
	  Assert.assertEquals(signuppage.passwordField.getAttribute("placeholder"), "Please Enter Your Password");
	  
	  // Google and facebook  login vervication
	  
	  Assert.assertTrue(signuppage.signWithGoogleLink.isDisplayed());
	  Assert.assertTrue(signuppage.signWithFacebookLink.isDisplayed());
	  
	  //check box verification
	  
	  Assert.assertTrue(signuppage.subscribeCheckBox.isDisplayed());
	  Assert.assertFalse(signuppage.subscribeCheckBox.isSelected());
	  Assert.assertTrue(signuppage.subcribeToNewsletter.isDisplayed());
	  
	  // back and signup button
	  
	  Assert.assertTrue(signuppage.signUpBtn.isDisplayed());
	  Assert.assertTrue(signuppage.backToLogin.isDisplayed());
	  
  }
  
  @AfterMethod (groups = "smoke-test")
  public void quitDriver() {
		Driver.quitDriver();
		
	}
}

