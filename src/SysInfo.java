public enum SysInfo {
    WINDOWS,LINUX,MACOS,ANDRIOD,OTHER;

    public static SysInfo checkSys(String str){
        str=str.toLowerCase();
        if (str.contains("windows") ||
                str.contains("win64") ||
                str.contains("x64"))
            return WINDOWS;
        else if (str.contains("linux") ||
                str.contains("ubuntu"))
            return LINUX;
        else if (str.contains("macos"))
            return MACOS;
        else if (str.contains("android"))
            return ANDRIOD;
        else
            return OTHER;
    }

}
