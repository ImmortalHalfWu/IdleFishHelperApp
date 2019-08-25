package browsers;

import com.teamdev.jxbrowser.chromium.Browser;

public class MyBrowser {

    private static MyBrowser myBrowser;

    public static MyBrowser instance() {
        if (myBrowser != null) {
            synchronized (MyBrowser.class) {
                if (myBrowser != null) {
                    myBrowser = new MyBrowser();
                }
            }
        }
        return myBrowser;
    }

    private MyBrowser() {
        browser = new Browser();
    }

    private Browser browser;


    public Browser initBrowser() {
        return browser;
    }



}
