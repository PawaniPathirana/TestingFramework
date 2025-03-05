package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import actions.HandleActions;
import base.Base;

public class HomePage extends Base{
	
HandleActions action= new HandleActions();
	
	@FindBy(xpath = "//a[normalize-space()='Login']") 
	private WebElement signInBtn;
	
	@FindBy(xpath = "//a[normalize-space()='Sign Up']")
	private WebElement signUpBtn;
	
	@FindBy(xpath="//input[@id='q']")
	private WebElement searchProductBox;
	
	@FindBy(xpath="//a[@class='search-box__button--1oH7']")
	private WebElement searchButton;
	
	public HomePage() {
		PageFactory.initElements(getDriver(), this);
	}
	
	public SearchResultPage searchProduct(String productName) throws Throwable {
		action.type(searchProductBox, productName);
		action.scrollByVisibilityOfElement(getDriver(), searchButton);
		action.click(getDriver(), searchButton);
		Thread.sleep(3000);
		return new SearchResultPage();
	}
	
public LoginPage loginCilck() {
	action.click(getDriver(), signInBtn);
	return new LoginPage();
	}

}