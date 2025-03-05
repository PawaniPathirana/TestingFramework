package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import actions.HandleActions;
import base.Base;

public class AddToCartPage extends Base{
	
	HandleActions action= new HandleActions();
	
	@FindBy(xpath="//input[@value='1']")
	private WebElement quantity;
	
	@FindBy(xpath="//span[@class='sku-variable-name-text'][normalize-space()='White-C']")
	private WebElement color;
	
	@FindBy(name="group_1")
	private WebElement size;
	
	@FindBy(xpath="//button[contains(@class,'pdp-button pdp-button_type_text pdp-button_theme_orange pdp-button_size_xl')]")
	private WebElement addToCartBtn;
	
	@FindBy(xpath="//div[@id='dialog-body-1']")
	private WebElement addToCartMessag;
	
	@FindBy(xpath="//button[contains(@class,'pdp-button pdp-button_type_text pdp-button_theme_bluedaraz pdp-button_size_xl')]")
	private WebElement buyNow;
	
	public AddToCartPage() {
		PageFactory.initElements(getDriver(), this);
	}

	public void enterQuantity(String quantity1) throws Throwable {
		action.type(quantity, quantity1);
	}
	
	public void selectSize(String size1) throws Throwable {
		action.selectByVisibleText(size1, size);
	}
	
	public void clickOnAddToCart() throws Throwable {
		action.click(getDriver(), addToCartBtn);
	}
	
	public boolean validateAddtoCart() throws Throwable {
		action.fluentWait(getDriver(), addToCartMessag, 10);
		return action.isDisplayed(getDriver(), addToCartMessag);
	}
	/*
	public OrderPage clickOnCheckOut() throws Throwable {
		action.fluentWait(getDriver(), proceedToCheckOutBtn, 10);
		action.JSClick(getDriver(), proceedToCheckOutBtn);
		return new OrderPage();
	}
	
	*/

}
