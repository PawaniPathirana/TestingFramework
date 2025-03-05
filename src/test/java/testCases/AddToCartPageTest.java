package testCases;

import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utility.Log;
import base.Base;
import pageObjects.AddToCartPage;
import pageObjects.HomePage;
import pageObjects.SearchResultPage;

public class AddToCartPageTest extends Base {

    private HomePage homePage;
    private SearchResultPage searchResultPage;
    private AddToCartPage addToCartPage;

    @Test
    @Parameters({"productName", "qty", "size"})
    public void addToCartTest(
            @Optional("Laptop") String productName,
            @Optional("1") String qty,
            @Optional("M") String size
    ) throws Throwable {
        Log.startTestCase("addToCartTest");

        homePage = new HomePage();
        searchResultPage = homePage.searchProduct(productName);
        addToCartPage = searchResultPage.clickOnProduct();
        addToCartPage.enterQuantity(qty);
        // addToCartPage.selectSize(size); // Uncomment if needed
        addToCartPage.clickOnAddToCart();

        boolean result = addToCartPage.validateAddtoCart();
        Assert.assertTrue(result);

        Log.endTestCase("addToCartTest");
    }
}