package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import utils.PropertyReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    protected WebDriverWait wait;
    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @BeforeSuite
    public void setUpSuite() {
        try {
            Files.createDirectories(Paths.get("test-output/reports"));
            Files.createDirectories(Paths.get("test-output/screenshots"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/reports/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("nopCommerce Automation Report");
        sparkReporter.config().setReportName("nopCommerce Test Automation Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // Disable Chrome password manager + leak detection popup
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            // Disable infobars, notifications, safety check & leak detection
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-features=PasswordManagerOnboarding,PasswordLeakDetection,ChromeSafetyCheck,NotificationTriggers");

            driver = new ChromeDriver(options);
            
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("start-maximized");

            // Use a common, up-to-date user-agent string
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        driver.get(PropertyReader.getProperty("url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String status;

        if (result.getStatus() == ITestResult.FAILURE) {
            status = "FAILURE";
            captureScreenshot(result.getName(), status);
            extentTest.get().log(Status.FAIL,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            extentTest.get().log(Status.FAIL,
                    MarkupHelper.createLabel("Failure Reason: " + result.getThrowable(), ExtentColor.RED));
        } else if (result.getStatus() == ITestResult.SKIP) {
            status = "SKIP";
            captureScreenshot(result.getName(), status);
            extentTest.get().log(Status.SKIP,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        } else {
            status = "SUCCESS";
            captureScreenshot(result.getName(), status);
            extentTest.get().log(Status.PASS,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
        }

        if (driver != null) {
            driver.quit();
        }
    }

    private void captureScreenshot(String testName, String status) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = testName + "_" + status + "_" + timeStamp + ".png";
            String screenshotPath = "test-output/screenshots/" + screenshotName;

            Files.copy(source.toPath(), new File(screenshotPath).toPath());

            extentTest.get().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
