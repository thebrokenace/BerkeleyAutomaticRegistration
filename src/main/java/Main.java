import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<String> courseCodes = new ArrayList<>();
    public static String emailAddress = "";
    public static String password = "";
    public static String user = "";

    public static void main(String... args) throws Exception {
        initializeCourseCodes();

        System.setProperty("webdriver.chrome.driver", ".\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = configureChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, 50);

        try {
            loginToPortal(driver, wait);
            navigateAndEnroll(driver, wait);
        } finally {
            driver.quit();
        }
    }

    private static void initializeCourseCodes() {
        //Add course codes here
        courseCodes.add("27724");
        courseCodes.add("24959");
        courseCodes.add("25276");
        courseCodes.add("25262");
    }

    private static ChromeOptions configureChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        //To prevent signout/session refresh, use old user profiles
        String userProfile = "C:\\Users\\" + user + "\\AppData\\Local\\Google\\Chrome\\User Data\\";

        options.addArguments("--user-data-dir=" + userProfile);
        options.addArguments("--profile-directory=Default");
        options.addArguments("--start-maximized");

        return options;
    }

    private static void loginToPortal(WebDriver driver, WebDriverWait wait) {
        driver.get("https://apply.berkeley.edu/account/login");
        driver.manage().window().maximize();

        WebElement email = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        WebElement pass = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        
        email.sendKeys(emailAddress);
        pass.sendKeys(password, Keys.ENTER);

        makeWait(1);
    }

    private static void navigateAndEnroll(WebDriver driver, WebDriverWait wait) {
        for (String courseCode : courseCodes) {
            driver.get("https://bcsweb.is.berkeley.edu/psc/bcsprd_newwin/EMPLOYEE/SA/c/CW_SR_MENU_ADMIN.CW_SSR_CLSRCH_FL.GBL");

            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CW_CLSRCH_WRK2_PTUN_KEYWORD")));
            searchBox.sendKeys(courseCode, Keys.ENTER);
            makeWait(2);

            if (driver.findElements(By.id("ptModFrame_0")).size() > 0) {
                processCourseFrame(driver, wait);
            }

            enrollInCourse(driver, wait);
        }
    }

    private static void processCourseFrame(WebDriver driver, WebDriverWait wait) {
        driver.switchTo().frame(driver.findElement(By.id("ptModFrame_0")));
        WebElement switchy = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='CW_CLS_DTL_WRK2_CW_OPEN_NONENROLL$0']")));
        retryingFindClick(switchy, driver);
    }

    private static void enrollInCourse(WebDriver driver, WebDriverWait wait) {
        WebElement enrollButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("CW_CLS_DTL_WRK2_SSS_ENRL_CART")));
        retryingFindClick(enrollButton, driver);

        makeWait(1);

        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title*='Next']")));
        new Actions(driver).moveToElement(nextButton).click().perform();

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("SSR_ENRL_FL_WRK_SUBMIT_PB")));
        new Actions(driver).moveToElement(submitButton).click().perform();

        makeWait(1);

        WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='#ICYes']")));
        retryingFindClick(confirmButton, driver);
    }

    public static boolean retryingFindClick(WebElement element, WebDriver driver) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                return true;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
            }
        }
        return false;
    }

    public static void makeWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
