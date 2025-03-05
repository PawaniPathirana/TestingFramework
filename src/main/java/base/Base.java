package base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
//import org.apache.log4j.xml.DOMConfigurator;  // Import Log4j's DOMConfigurator
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import utility.Base_URL;
import utility.Log;  // Import the Log class

public class Base {

    public static WebDriver driver;
    
    
    public static WebDriver getDriver() {
        return driver;
    }
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    // Run this method before the test starts to load the log4j configuration
    @BeforeTest
    public void beforeTestMethod() {
        // Configure Log4j with the XML configuration file
        //DOMConfigurator.configure("src/main/resources/log4j2.xml"); // Add this line to configure Log4j

        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "TestReport");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        sparkReporter.config().setTheme(Theme.DARK);
        extent.setSystemInfo("Host Name", "RHEL8");
        extent.setSystemInfo("User Name", "root");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Automation Tests Results");
    }

    @BeforeMethod
    @Parameters("browser")
    public void beforeMethodMethod(@Optional("chrome") String browser, Method testMethod) {
        logger = extent.createTest(testMethod.getName());
        Log.info("Starting Test Case: " + testMethod.getName());
        setupDriver(browser);
        driver.manage().window().maximize();
        driver.get(Base_URL.url);
        Log.info("Navigated to " + Base_URL.url);  // Log the URL navigation
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable().toString(), ExtentColor.RED));

            // Log failure in the Log class
            Log.error("Test case failed: " + result.getName());
            // Capture screenshot
            String screenshotPath = captureScreenshot(driver, result.getName());
            if (screenshotPath != null) {
                logger.fail("Screenshot of failure:", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
            Log.warn("Test case skipped: " + result.getName());  // Log skipped tests
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
            Log.info("Test case passed: " + result.getName());  // Log passed tests
        }
        driver.quit();
    }

    // Screenshot Utility Method
    public String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            return dest;
        } catch (IOException e) {
            Log.error("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }

    @AfterTest
    public void afterTest() {
        extent.flush();
    }

    public void setupDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            Log.info("Chrome driver is set up.");
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            Log.info("Firefox driver is set up.");
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            Log.info("Edge driver is set up.");
        }
    }
}
