public class LogRecord {
    private String addres; // IP-адрес клиента, который сделал запрос к серверу (в примере выше — 37.231.123.209).

    private String dummy1; // Два пропущенных свойства, на месте которых обычно стоят дефисы, но могут встречаться также и пустые строки ("").

    private String dummy2;

    private String dateRec; // Дата и время запроса в квадратных скобках.

    private String method; // Метод запроса (в примере выше — GET) и путь, по которому сделан запрос.

    private String responseCode; // Код HTTP-ответа (в примере выше — 200).

    private String responseSize; // Размер отданных данных в байтах (в примере выше — 61096).

    private String referer; // Путь к странице, с которой перешли на текущую страницу, — referer (в примере выше — “https://nova-news.ru/search/?rss=1&lg=1”).

    private String userAgent; // User-Agent — информация о браузере или другом клиенте, который выполнил запрос.

    private LogRecord() {
    }

    private LogRecord(String addres, String dummy1, String dummy2, String dateRec, String method, String responseCode, String responseSize, String referer, String userAgent) {
        this.addres = addres;
        this.dummy1 = dummy1;
        this.dummy2 = dummy2;
        this.dateRec = dateRec;
        this.method = method;
        this.responseCode = responseCode;
        this.responseSize = responseSize;
        this.referer = referer;
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return addres + " " +
                dummy1 + " " +
                dummy2 + " " +
                dateRec +" " +
                "\""+method +"\"" + " " +
                responseCode + " " +
                responseSize + " " +
                "\""+referer  +"\"" + " " +
                "\""+userAgent+"\"";
    }

    public static LogRecord of(String str){
        LogRecord logRecord=new LogRecord();
        String str1;
        int i;
        i=str.indexOf(' ');
        logRecord.addres=str.substring(0,i);
//        System.out.println(logRecord.addres);
        i++;
        str=str.substring(i);
//        System.out.println(str);

        i=str.indexOf(' ');
        logRecord.dummy1=str.substring(0,i);
//        System.out.println(logRecord.dummy1);
        i++;
        str=str.substring(i);

        i=str.indexOf(' ');
        logRecord.dummy2=str.substring(0,i);
//        System.out.println(logRecord.dummy2);
        i++;
        str=str.substring(i);

        i=str.indexOf(']');
        logRecord.dateRec=str.substring(0,i+1);
//        System.out.println(logRecord.dateRec);
        i+=3;
        str=str.substring(i);

        i=str.indexOf('\"');
        logRecord.method=str.substring(0,i);
//        System.out.println(logRecord.method);
        i+=2;
        str=str.substring(i);

        i=str.indexOf(' ');
        logRecord.responseCode=str.substring(0,i);
//        System.out.println(logRecord.responseCode);
        i++;
        str=str.substring(i);

        i=str.indexOf(' ');
        logRecord.responseSize=str.substring(0,i);
//        System.out.println(logRecord.responseSize);
        i+=2;
        str=str.substring(i);

        i=str.indexOf('\"');
        logRecord.referer=str.substring(0,i);
//        System.out.println(logRecord.referer);
        i+=3;
        str=str.substring(i);
//        System.out.println(str);

        i=str.indexOf('\"');
        logRecord.userAgent=str.substring(0,i-1);
//        System.out.println(logRecord.userAgent);

        return logRecord;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
