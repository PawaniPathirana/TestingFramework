package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import actions.HandleActions;
import base.Base;

public class LoginPage extends Base{
	
HandleActions action= new HandleActions();

@FindBy(xpath="//div[contains(@class,'iweb-dialog-container-enter')]//input[contains(@placeholder,'Please enter your Phone Number or Email')]")
private WebElement enterEmail;

@FindBy(xpath="//div[contains(@class,'iweb-dialog-container-enter')]//input[contains(@placeholder,'Please enter your password')]")
private WebElement enterPassword;

@FindBy(xpath="//div[contains(@class,'iweb-dialog-container-enter')]//div[contains(@class,'iweb-button-mask')]")
private WebElement loginBtn;

public void performLogin (String email, String password) {
	
	action.type(enterEmail, email);
	action.type(enterPassword, password);
	action.click(getDriver(),loginBtn);
}


}
