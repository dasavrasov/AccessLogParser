public class UserAgent {
    private final SysInfo sys;
    private final BrowserInfo browser;

    public SysInfo getSys() {
        return sys;
    }

    public BrowserInfo getBrowser() {
        return browser;
    }

    public UserAgent(String str) {
       this.sys=SysInfo.checkSys(str);
        this.browser=BrowserInfo.checkBrowser(str);
    }
}
