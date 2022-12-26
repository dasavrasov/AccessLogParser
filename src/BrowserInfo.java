public enum BrowserInfo {
    FIREFOX,CHROME,SAFARI,OPERA,IE,OTHER;

    public static BrowserInfo checkBrowser(String str){
        str=str.toLowerCase();
        if (str.contains("firefox"))
            return FIREFOX;
        else if (str.contains("chrome") || str.contains("chromium"))
            return CHROME;
        else if (str.contains("opera"))
            return OPERA;
        else if (str.contains("safari") && !(str.contains("chrome") || str.contains("chromium")))
            return SAFARI;
        else if (str.contains("msie") || str.contains("trident"))
            return IE;
        else
            return OTHER;
    }

}
