import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.HasAndroidDeviceDetails;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 *  Selenium Singleton Class
 */

public class Driver {

    // local variables
    private static Driver instance = null;
    private String browserHanle = null;
    private static final int IMPLICIT_TIMEOUT = 0;

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    private ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<AppiumDriver<MobileElement>>();

    private ThreadLocal<String> sessionId = new ThreadLocal<String>();
    private ThreadLocal<String> sessionBrowser = new ThreadLocal<String>();
    private ThreadLocal<String> sessionPlatform = new ThreadLocal<String>();
    private ThreadLocal<String> sessionVersion = new ThreadLocal<String>();

    private String getEnv = null;
    private Properties props = new Properties();

    private Driver() {

    }

    public static Driver getInstance() {
        if (instance == null) {
            instance = new Driver();
        }
        return instance;
    }

    /**
     * setDriver method to initiate Selenium/Appium driver depending on the parameters passed in to this method.
     *
     * @param browser
     * @param platform
     * @param environment
     * @param optPreferences
     * @throws MalformedURLException
     */
    @SafeVarargs
    public final void setDriver(String browser,
                                String platform,
                                String environment,
                                Map<String, Object>... optPreferences) throws MalformedURLException {

        DesiredCapabilities caps = null;
        String localHub = "http://127.0.0.1:4723/wd/hub";
        getEnv = environment;
        String getPlatform = platform;

        switch(browser) {
            case "firefox":
                caps = DesiredCapabilities.firefox();

                FirefoxOptions ffOpts = new FirefoxOptions();
                FirefoxProfile ffProfile = new FirefoxProfile();

                ffProfile.setPreference("browser.autofocus", true);
                ffProfile.setPreference("browser.tabs.remote.autostart.2", false);

                caps.setCapability(FirefoxDriver.PROFILE, ffProfile);
                caps.setCapability("marionette", true);

                if ( environment.equalsIgnoreCase("local") ) {
                    System.setProperty("webdriver.gecko.driver", "../resources/drivers/geckodriver");
                }

                if ( optPreferences.length > 0 ) {
                    processFFProfile(ffProfile, optPreferences);
                }

                webDriver.set(new FirefoxDriver(ffOpts.merge(caps)));


                break;
            case "chrome":
                caps = DesiredCapabilities.chrome();

                ChromeOptions chOptions = new ChromeOptions();
                Map<String, Object> chromePrefs = new HashMap<String, Object>();

                chromePrefs.put("credentials_enable_service", false);

                chOptions.setExperimentalOption("prefs", chromePrefs);
                chOptions.addArguments("--disable-plugins", "--disable-extensions", "--disable-popup-blocking");

                caps.setCapability(ChromeOptions.CAPABILITY, chOptions);
                caps.setCapability("applicationCacheEnabled", false);

                if ( environment.equalsIgnoreCase("local") ) {
                    System.setProperty("webdriver.chrome.driver", "../resources/drivers/chromedriver");
                }

                if ( optPreferences.length > 0 ) {
                    processCHProfile(chOptions, optPreferences);
                }

                webDriver.set(new ChromeDriver(chOptions.merge(caps)));

                break;
            case "internet explorer":
                caps = DesiredCapabilities.internetExplorer();

                InternetExplorerOptions ieOpts = new InternetExplorerOptions();

                ieOpts.requireWindowFocus();
                ieOpts.merge(caps);

                caps.setCapability("requireWindowFocus", true);

                if ( optPreferences.length > 0 ) {
                    processDesiredCaps(caps, optPreferences);
                }

                webDriver.set(new InternetExplorerDriver(ieOpts.merge(caps)));

                break;
            case "safari":
                caps = DesiredCapabilities.safari();

                SafariOptions safariOpts = new SafariOptions();
                safariOpts.merge(caps);

                caps.setCapability(SafariOptions.CAPABILITY, safariOpts);
                caps.setCapability("autoAcceptAlerts", true);
                webDriver.set(new SafariDriver(safariOpts.merge(caps)));

                break;
            case "microsoftedge":
                caps = DesiredCapabilities.edge();

                EdgeOptions edgeOpts = new EdgeOptions();
                edgeOpts.setPageLoadStrategy("normal");

                webDriver.set(new EdgeDriver(edgeOpts.merge(caps)));

                break;
            case "iphone":
            case "ipad":
                if (browser.equalsIgnoreCase("ipad")) {
                    caps = DesiredCapabilities.ipad();
                }

                else {
                    caps = DesiredCapabilities.iphone();
                }

                mobileDriver.set(new IOSDriver<MobileElement>(new URL(localHub), caps));

                break;
            case "android":
                caps = DesiredCapabilities.android();
                mobileDriver.set(new AndroidDriver<MobileElement>(new URL(localHub), caps));

                break;
        }

        if ( browser.equalsIgnoreCase("iphone") || browser.equalsIgnoreCase("android")) {
            sessionId.set(((IOSDriver<MobileElement>) mobileDriver.get()).getSessionId().toString());
            sessionId.set(((AndroidDriver<MobileElement>) mobileDriver.get()).getSessionId().toString());
            sessionBrowser.set(browser);
            sessionVersion.set(caps.getCapability("deviceName").toString());
            sessionPlatform.set(getPlatform);
        } else {
            sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
            sessionBrowser.set(caps.getBrowserName());
            sessionVersion.set(caps.getVersion());
            sessionPlatform.set(getPlatform);
        }
    }

    /**
     * Overloaded setDriver method to swith driver to specific WebDriver instance if running concurrent drivers.
     *
     * @param driver WebDriver instance to switch to
     */
    public void setDriver(WebDriver driver) {
        webDriver.set(driver);

        sessionId.set(((RemoteWebDriver)webDriver.get()).getSessionId().toString());
        sessionBrowser.set(((RemoteWebDriver)webDriver.get()).getCapabilities().getBrowserName());
        sessionPlatform.set(((RemoteWebDriver)webDriver.get()).getCapabilities().getPlatform().toString());

//        setBrowserHandle(getDriver().getWindowHandle());
    }

    /**
     * Overloaded setDriver method to swith driver to specific AppiumDriver instance if running concurrent drivers.
     *
     * @param driver AppiumDriver instance to switch to
     */
    public void setDriver(AppiumDriver<MobileElement> driver) {
        mobileDriver.set(driver);

        sessionId.set((mobileDriver.get()).getSessionId().toString());
        sessionBrowser.set((mobileDriver.get()).getCapabilities().getBrowserName());
        sessionPlatform.set((mobileDriver.get()).getCapabilities().getPlatform().toString());

    }

    /**
     * getDriver method will retrieve the active WebDriver
     *
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return webDriver.get();
    }

    /**
     * getMobileDriver method will retrieve the active AppiumDriver
     *
     * @return AppiumDriver
     */
    public AppiumDriver<MobileElement> getMobileDriver() {
        return mobileDriver.get();
    }

    /**
     * getCurrentDriver method will retrieve the active WebDriver or AppiumDriver
     *
     * @return WebDriver
     */
    public WebDriver getCurrentDriver() {
        if (getInstance().getSessionBrowser().contains("iphone")
                || getInstance().getSessionBrowser().contains("ipad")
                || getInstance().getSessionBrowser().contains("android")) {
            return getInstance().getMobileDriver();
        } else {
            return getInstance().getDriver();
        }
    }

    /**
     * driverWait method pauses the driver in seconds
     *
     * @param seconds to pause
     */
    public void driverWait(long seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * driverRefresh method reloads the current browser page
     */
    public void driverRefresh() {
        getCurrentDriver().navigate().refresh();
    }

    /**
     * closeDriver method quits the current active driver
     */
    public void closeDriver() {
        try {
            getCurrentDriver().quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getSessionId method gets the browser or mobile id of the active session
     *
     * @return String
     */
    public String getSessionId() {
        return sessionId.get();
    }


    /**
     * getSessionBrowser method gets the browser or mobile type of the active session
     *
     * @return String
     */
    public String getSessionBrowser() {
        return sessionBrowser.get();
    }

    /**
     * getSessionVersion method gets the browser or mobile version of the active session
     *
     * @return String
     */
    public String getSessionVersion() {
        return sessionVersion.get();
    }

    /**
     * getSessionPlatform method gets the browser or mobile platform of the active session
     *
     * @return String
     */
    public String getSessionPlatform() {
        return sessionPlatform.get();
    }

    /**
     * ProcessDesiredCaps method to override default browser or mobile driver behaviour
     *
     * @param caps - the DesiredCapabilities object
     * @param options - the key: value pair map
     */
    private void processDesiredCaps(DesiredCapabilities caps, Map<String, Object>[] options) {
        for ( int i = 0; i < options.length; i++ ) {
            Object[] keys = options[i].keySet().toArray();
            Object[] values = options[i].values().toArray();

            for( int j = 0; j < keys.length; j++ ) {
                if ( values[j] instanceof Integer ) {
                    caps.setCapability(keys[j].toString(), (int) values[j]);
                } else if ( values[j] instanceof Boolean ) {
                    caps.setCapability(keys[j].toString(), (boolean) values[j]);
                } else {
                    caps.setCapability(keys[j].toString(), values[j].toString());
                }
            }
        }
    }

    /**
     * ProcessFFProfile method to override default browser or mobile driver behaviour
     *
     * @param profile - the FireFox profile object
     * @param options - the key: value pair map
     */
    private void processFFProfile(FirefoxProfile profile,  Map<String, Object>[] options) {
        for ( int i = 0; i < options.length; i++ ) {
            Object[] keys = options[i].keySet().toArray();
            Object[] values = options[i].values().toArray();

            for( int j = 0; j < keys.length; j++ ) {
                if ( values[j] instanceof Integer ) {
                    profile.setPreference(keys[j].toString(), (int) values[j]);
                } else if ( values[j] instanceof Boolean ) {
                    profile.setPreference(keys[j].toString(), (boolean) values[j]);
                } else {
                    profile.setPreference(keys[j].toString(), values[j].toString());
                }
            }
        }
    }

    /**
     * ProcessCHProfile method to override default browser or mobile driver behaviour
     *
     * @param chOptions - the ChromeOptions profile object
     * @param options - the key: value pair map
     */
    private void processCHProfile(ChromeOptions chOptions,  Map<String, Object>[] options) {
        for ( int i = 0; i < options.length; i++ ) {
            Object[] keys = options[i].keySet().toArray();
            Object[] values = options[i].values().toArray();

            for( int j = 0; j < keys.length; j++ ) {
                if ( values[j] instanceof Integer ) {
                    chOptions.setExperimentalOption(keys[j].toString(), (int) values[j]);
                } else if ( values[j] instanceof Boolean ) {
                    chOptions.setExperimentalOption(keys[j].toString(), (boolean) values[j]);
                } else {
                    chOptions.setExperimentalOption(keys[j].toString(), values[j].toString());
                }
            }
        }
    }


}
