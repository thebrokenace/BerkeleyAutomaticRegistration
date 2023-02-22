import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Main {

    public static List<String> courseCodes = new ArrayList<>();
    public static String emailAddress = "";
    public static String password = "";
    public static String user = "";
        static public void main(String... args) throws Exception {
        courseCodes.add("27724");
        courseCodes.add("24959");
        courseCodes.add("25276");
        courseCodes.add("25262");


        System.setProperty("webdriver.chrome.driver", ".\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        String userProfile= "C:\\Users\\" + user + "\\AppData\\Local\\Google\\Chrome\\User Data\\";

        options.addArguments("--user-data-dir="+userProfile);
        options.addArguments("--profile-directory=Default");
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 50);


        driver.get("https://apply.berkeley.edu/account/login?r=https%3a%2f%2fapply.berkeley.edu%2faccount%2fcas%2flogin%3fservice%3dhttps%253A%252F%252Fauth.berkeley.edu%252Fcas%252Flogin%252FSlate");
        driver.manage().window().maximize();

        WebElement email = wait.until(presenceOfElementLocated(By.name("email")));
        WebElement pass = wait.until(presenceOfElementLocated(By.name("password")));
        email.click();
        email.sendKeys(emailAddress);
        pass.click();
        pass.sendKeys(password);
        pass.sendKeys(Keys.ENTER);
        makeWait(1);
        driver.get("https://bcsweb.is.berkeley.edu/psc/bcsprd/EMPLOYEE/SA/c/SSR_STUDENT_FL.SSR_MD_SP_FL.GBL?Action=U&MD=Y&GMenu=SSR_STUDENT_FL&GComp=SSR_START_PAGE_FL&GPage=SSR_START_PAGE_FL&scname=CS_SSR_MANAGE_CLASSES_NAV&AJAXTransfer=y&ICAJAXTrf=true&ICMDListSlideout=true&ucFrom=CalCentral&ucFromText=My%20Academics&ucFromLink=https%3A%2F%2Fcalcentral.berkeley.edu%2Facademics");
        WebElement button = wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"loginForm\"]/fieldset/span[3]/a")));
        button.click();

        try {
            for (String courseCode : courseCodes) {

                driver.get("https://bcsweb.is.berkeley.edu/psc/bcsprd_newwin/EMPLOYEE/SA/c/CW_SR_MENU_ADMIN.CW_SSR_CLSRCH_FL.GBL?Page=CW_CLSRCH_FL&pslnkid=CW_S201905011129247222902047&ICAJAXTrf=true");
                driver.switchTo().parentFrame();

                JavascriptExecutor js;
                js = (JavascriptExecutor) driver;

                js.executeScript("javascript:OnRowAction(this,'SSR_CSTRMCUR_VW_DESCR$1');cancelBubble(event);");


                wait.until(ExpectedConditions.elementToBeClickable(By.id("CW_CLSRCH_WRK2_PTUN_KEYWORD")));

                WebElement searchBox = wait.until(presenceOfElementLocated(By.id("CW_CLSRCH_WRK2_PTUN_KEYWORD")));
                searchBox.click();
                searchBox.sendKeys(courseCode);
                searchBox.sendKeys(Keys.ENTER);
                makeWait(2);
                if (driver.findElements(By.id("ptModFrame_0")).size() > 0) {
                    System.out.print("form accessed");

//                    WebElement form = driver.findElement(By.id("CW_CLS_DTL_WRK2_SSS_ENRL_CART"));
//                    form.click();
                    System.out.print(driver.getTitle());
                    driver.findElement(By.id("ptModFrame_0")).click();
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("ptModFrame_0")));

                    driver.switchTo().frame(driver.findElement(By.id("ptModFrame_0")));


                    System.out.print(driver.getTitle());


                }


                if (driver.findElements(By.cssSelector("label[for='CW_CLS_DTL_WRK2_CW_OPEN_NONENROLL$0']")).size() > 0) {
                    WebElement switchy = wait.until(elementToBeClickable(By.cssSelector("label[for='CW_CLS_DTL_WRK2_CW_OPEN_NONENROLL$0']")));
                    retryingFindClick(switchy, driver);
                }
                if (driver.findElements(By.cssSelector("label[for='CW_CLS_DTL_WRK2_CW_OPEN_NONENROLL$1']")).size() > 0) {
                    WebElement switchy = wait.until(elementToBeClickable(By.cssSelector("label[for='CW_CLS_DTL_WRK2_CW_OPEN_NONENROLL$1']")));
                    retryingFindClick(switchy, driver);
                }

                if (driver.findElements(By.id("CW_CLSRCH_N2_DV$sels$0$$0")).size() > 0) {
                    WebElement switchy = wait.until(elementToBeClickable(By.id("CW_CLSRCH_N2_DV$sels$0$$0")));
                    retryingFindClick(switchy, driver);

                }
                if (driver.findElements(By.id("CW_CLSRCH_N2_DV$sels$1$$1")).size() > 0) {
                    WebElement switchy = wait.until(elementToBeClickable(By.id("CW_CLSRCH_N2_DV$sels$1$$1")));
                    retryingFindClick(switchy, driver);

                }

                WebElement element = wait.until(elementToBeClickable(By.id("CW_CLS_DTL_WRK2_SSS_ENRL_CART")));
                retryingFindClick(element, driver);

                driver.switchTo().parentFrame();

                makeWait(1);


                WebElement s = wait.until(elementToBeClickable(By.cssSelector("span[title*='Next']")));
                Actions actions = new Actions(driver);
                actions.moveToElement(s).click().build().perform();
                retryingFindClick(s, driver);

                makeWait(1);


                WebElement switchy = wait.until(visibilityOfElementLocated(By.cssSelector("div[class*='ps_box-checkbox ps_switch psc_label-none psc_display-inlineblock psc_float-right']")));
                retryingFindClick(switchy, driver);

                makeWait(1);

                WebElement accept = wait.until(elementToBeClickable(By.cssSelector("span[title*='Select']")));
                retryingFindClick(accept, driver);



                makeWait(1);
                WebElement butt = wait.until(elementToBeClickable(By.id("PTGP_GPLT_WRK_PTGP_NEXT_PB")));

                actions.moveToElement(butt).click().build().perform();

                retryingFindClick(butt, driver);
                makeWait(1);

                WebElement submit = wait.until(elementToBeClickable((By.id("SSR_ENRL_FL_WRK_SUBMIT_PB"))));
                actions.moveToElement(submit).click().build().perform();

                retryingFindClick(submit, driver);

                makeWait(1);



                WebElement dialog = driver.findElement(By.id("ptModTable_0"));
                System.out.print(dialog.findElements(By.xpath("//*[@id=\"#ICYes\"]")));
                if (dialog.findElements(By.xpath("//*[@id=\"#ICYes\"]")).size() > 0) {
                    System.out.print("found yes button");
                }

                WebElement yes = wait.until(visibilityOfElementLocated(By.xpath("//*[@id=\"#ICYes\"]")));
                retryingFindClick(yes, driver);

                makeWait(5);
                js.executeScript("javascript:LaunchURL(null,'https://bcsweb.is.berkeley.edu/psc/bcsprd_newwin/EMPLOYEE/SA/c/CW_SR_MENU_ADMIN.CW_SSR_CLSRCH_FL.GBL?Page=CW_CLSRCH_FL&pslnkid=CW_S201905011129247222902047&ICAJAXTrf=true',7,'',this.id);cancelBubble(event);");

                //CW_CLSRCH_N2_DV$sels$0$$0
            }

        } finally {
            //driver.quit();

        }


    }
    public static boolean retryingFindClick(WebElement by, WebDriver driver) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 6) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", by);
                try {

                            by.click();

                } catch (ElementClickInterceptedException | NoSuchWindowException ee) {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(by).click().build().perform();
                }
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
//                Actions actions = new Actions(driver);
//                actions.moveToElement(by).click().build().perform();
//                by.click();
            }
            attempts++;
        }
        return result;
    }
    public static void makeWait(int waitForSecond)
    {
        try {
            //Thread.sleep(1000 * waitForSecond);
            Thread.currentThread().sleep(1000 * waitForSecond);
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }
}
