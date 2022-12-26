import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    private static final DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.ENGLISH);

    private final String addres; // IP-адрес клиента, который сделал запрос к серверу (в примере выше — 37.231.123.209).

    private final String dummy1; // Два пропущенных свойства, на месте которых обычно стоят дефисы, но могут встречаться также и пустые строки ("").

    private final String dummy2;

    private final LocalDateTime dateRec; // Дата и время запроса в квадратных скобках.

    private final HttpMethod method; // Метод запроса (в примере выше — GET) и путь, по которому сделан запрос.

    private final int responseCode; // Код HTTP-ответа (в примере выше — 200).

    private final int responseSize; // Размер отданных данных в байтах (в примере выше — 61096).

    private final String referer; // Путь к странице, с которой перешли на текущую страницу, — referer (в примере выше — “https://nova-news.ru/search/?rss=1&lg=1”).

    private final UserAgent userAgent; // User-Agent — информация о браузере или другом клиенте, который выполнил запрос.

    @Override
    public String toString() {
        return addres + " " +
                dateRec +" " +
                "\""+method +"\"" + " " +
                responseCode + " " +
                responseSize + " " +
                "\""+referer  +"\"" + " " +
                userAgent.getSys()+" " +
                userAgent.getBrowser();
    }

    public LogEntry(String str){
        int i;
        i=str.indexOf(' ');
        this.addres=str.substring(0,i);
        i++;
        str=str.substring(i);

        i=str.indexOf(' ');
        this.dummy1=str.substring(0,i);
        i++;
        str=str.substring(i);

        i=str.indexOf(' ');
        this.dummy2=str.substring(0,i);
        i++;
        str=str.substring(i);

        i=str.indexOf(']');
        String strDate=str.substring(0,i+1); //дата в квадратных скобках []
        strDate = strDate.substring(1,strDate.length()-1);//убираем []
        try {
            this.dateRec=LocalDateTime.parse(strDate,dtf); //парсим дату
        } catch (Exception e) {
            throw new RuntimeException("Ошибка парсинга даты запроса "+strDate);
        }
        i+=3;
        str=str.substring(i);

        i=str.indexOf('\"');
        this.method=GetHttpMethod(str.substring(0,i));
        i+=2;
        str=str.substring(i);

        i=str.indexOf(' ');
        try {
            this.responseCode=Integer.parseInt(str.substring(0,i));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        i++;
        str=str.substring(i);

        i=str.indexOf(' ');
        try {
            this.responseSize=Integer.parseInt(str.substring(0,i));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        i+=2;
        str=str.substring(i);

        i=str.indexOf('\"');
        this.referer=str.substring(0,i);
        i+=3;
        str=str.substring(i);

        i=str.indexOf('\"');
        this.userAgent=new UserAgent(str.substring(0,i-1));
    }

    private HttpMethod GetHttpMethod(String str) {
        int i=0;
        i=str.indexOf(' ');
        String s_meth=str.substring(0,i).trim();
        return HttpMethod.valueOf(s_meth);
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public String getAddres() {
        return addres;
    }

    public LocalDateTime getDateRec() {
        return dateRec;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }
}
