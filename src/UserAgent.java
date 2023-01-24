public class UserAgent {
    private final SysInfo sys;
    private final BrowserInfo browser;

    private String strValue;

    public boolean isBot() {
        return strValue.toLowerCase().contains("bot");
    }

    public SysInfo getSys() {
        return sys;
    }

    public BrowserInfo getBrowser() {
        return browser;
    }

    public String getStrValue() {
        return strValue;
    }

    public UserAgent(String str) {
       this.sys=SysInfo.checkSys(str);
       this.browser=BrowserInfo.checkBrowser(str);
       this.strValue=str;
    }
}
