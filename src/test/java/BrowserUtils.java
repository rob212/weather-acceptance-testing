import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtils {

    /**
     * waitFor method to wait up to a designated period before throwing exception (static locator)
     *
     * @param element
     * @param timer
     */
    public static void waitFor(WebElement element, int timer) {
        WebDriver driver = Driver.getInstance().getDriver();

        // wait for the static element to appear
        WebDriverWait exists = new WebDriverWait(driver, timer);
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
    }

    /**
     * overloaded waitFor method to wait up to a designated period before throwing exception (dynamic locator)
     *
     * @param by
     * @param timer
     */
    public static void waitFor(By by, int timer) {
        WebDriver driver = Driver.getInstance().getDriver();

        // wait for the dynamic element to appear
        WebDriverWait exists = new WebDriverWait(driver, timer);

        // examples: By.id(id), By.name(name), By.xpath(locator), By.css(css)
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
    }

    /**
     * waitForGone method to wait up to a designated period before throwing exception if element still exists
     *
     * @param by
     * @param timer
     */
    public static void waitForGone(By by, int timer) {
        WebDriver driver = Driver.getInstance().getDriver();

        // wait for the dynamic element to disappear
        WebDriverWait exists = new WebDriverWait(driver, timer);

        // examples: By.id(id), By.name(name), By.xpath(locator), By.css(css)
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOfElementLocated(by)));
    }

    /**
     * waitForURL method to wait up to a designated period before throwing exception if URL is not found
     *
     * @param url
     * @param timer
     */
    public static void waitForURL(String url, int timer) {
        WebDriver driver = Driver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, timer);

        exists.until(ExpectedConditions.refreshed(ExpectedConditions.urlContains(url)));
    }

    /**
     * waitForTitle method to wait up to a designated period before throwing exception if Title is not found
     *
     * @param title
     * @param timer
     */
    public static void waitForTitle(String title, int timer) {
        WebDriver driver = Driver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, timer);

        exists.until(ExpectedConditions.refreshed(ExpectedConditions.titleContains(title)));
    }
}
