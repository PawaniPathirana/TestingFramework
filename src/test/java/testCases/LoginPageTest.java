package testCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.Base;
import pageObjects.HomePage;
import pageObjects.LoginPage;

public class LoginPageTest extends Base{
	
	private LoginPage loginPage;
	
	@Parameters({"email", "password"})
	@Test
	public void loginTest( @Optional("rttd@gmail.com")String email, @Optional("rt@td") String password){ 
		
		
	    HomePage homePage = new HomePage();
	    
	    loginPage = homePage.loginCilck();
	    
	    loginPage.performLogin(email, password);
	}

	

}
