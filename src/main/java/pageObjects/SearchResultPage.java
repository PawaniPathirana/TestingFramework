package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import actions.HandleActions;
import base.Base;

public class SearchResultPage extends Base{
	
	HandleActions action= new HandleActions();
	
	@FindBy(xpath="//div[@class='ant-col ant-col-20 ant-col-push-4 Jv5R8 css-1bkhbmc app']//div[1]//div[1]//div[1]//div[1]//div[1]//a[1]//div[1]//img[1]")
	private WebElement productResult;
	
	public SearchResultPage() {
		PageFactory.initElements(getDriver(), this);
	}
	
	public boolean isProductAvailable() throws Throwable {
		return action.isDisplayed(getDriver(), productResult);
	}
	
	public AddToCartPage clickOnProduct() throws Throwable {
		action.click(getDriver(), productResult);
		return new AddToCartPage();
	}
}
