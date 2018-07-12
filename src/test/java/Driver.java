import org.openqa.selenium.WebDriver;

/**
 *  Selenium Singleton Class
 */

public class Driver {

    // local variables
    private static Driver instance = null;
    private String browserHanle = null;
    private static final int IMPLICIT_TIMEOUT = 0;

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();


    private Driver() {

    }

    public static Driver getInstance() {
        if (instance == null) {
            instance = new Driver();
        }
        return instance;
    }

}
