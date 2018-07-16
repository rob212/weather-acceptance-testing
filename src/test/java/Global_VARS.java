import java.io.File;

public class Global_VARS {
    public static final String BROWSER = "firefox";
    public static final String PLATFORM = "Windows 10";
    public static final String ENVIRONMENT = "local";
    public static String DEF_BROWSER = null;
    public static String DEF_PLATFORM = null;
    public static String DEF_ENVIRONMENT = null;
    public static String PROPS_PATH = null;

    // driver class defaults
    public static String propFile = "../resources/selenium.properties";
    public static final String SE_PROPS = new File(propFile).getAbsolutePath();

    // test output path outputs
    public static final String TEST_OUTPUT_PATH = "testOutput/";
    public static final String LOGFILE_PATH = TEST_OUTPUT_PATH + "Logs/";
    public static final String REPORT_PATH = TEST_OUTPUT_PATH + "Reports/";
    public static final String BITMAP_PATH = TEST_OUTPUT_PATH + "Bitmaps/";

    // timeout defaults
    public static final int TIMEOUT_MINUTES = 60;
    public static final int TIMEOUT_SECONDS = 1;
    public static final int TIMEOUT_ZERO = 0;
}
