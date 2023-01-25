import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Statistics {
    private long totalTraffic=0;
    private LocalDateTime minTime=LocalDateTime.MAX;

    private LocalDateTime maxTime=LocalDateTime.MIN;


    private HashSet<String> domains=new HashSet<>();

    private HashMap<Integer, Integer> activities=new HashMap<>();

    private HashMap<String, Integer> userVisits=new HashMap<>();

    public long getHours(){
        return Duration.between(minTime,maxTime).toHours();
    }
    private HashSet<String> existingPages =new HashSet<>(); //список существующих страниц

    private HashSet<String> nonExistingPages =new HashSet<>(); //список несуществующих страниц

    private HashMap<String, Integer> opsysStats=new HashMap<>();

    private HashMap<String, Integer> browserStats=new HashMap<>();

    private List<LogEntry> lines=new ArrayList<>(); //все строки файла

    public HashMap<String, Integer> getOpsysStats() {
        return opsysStats;
    }

    public HashMap<String, Integer> getBrowserStats() {
        return browserStats;
    }

    public void addEntry(LogEntry logEntry){
        SysInfo sysInfo;
        BrowserInfo browserInfo;
        int responseCode=0;
        int sec=0;
        int visitsPerSec=0;
        int visitsPerUser=0;
        String user;

        lines.add(logEntry);

        totalTraffic+=logEntry.getResponseSize();
        if (logEntry.getDateRec().compareTo(minTime)<0)
            minTime=logEntry.getDateRec();
        if (logEntry.getDateRec().compareTo(maxTime)>0)
            maxTime=logEntry.getDateRec();
        responseCode=logEntry.getResponseCode();
        if (responseCode==200)
            existingPages.add(logEntry.getAddress());
        else if (responseCode==404) {
            nonExistingPages.add(logEntry.getAddress());
        }
        //заполнение статистики операционных систем
        sysInfo=logEntry.getUserAgent().getSys();
        if (!opsysStats.containsKey(sysInfo.name()))
            opsysStats.put(sysInfo.name(),1);
        else
            opsysStats.put(sysInfo.name(),opsysStats.get(sysInfo.name())+1);

        //заполнение статистики браузеров
        browserInfo=logEntry.getUserAgent().getBrowser();
        if (!browserStats.containsKey(browserInfo.name()))
            browserStats.put(browserInfo.name(),1);
        else
            browserStats.put(browserInfo.name(),browserStats.get(browserInfo.name())+1);

        if (!logEntry.getUserAgent().isBot()) { //если не Бот
            sec=logEntry.getDateRec().getSecond();
            if (activities.containsKey(sec)) {
                visitsPerSec = activities.get(sec);
                activities.put(sec, ++visitsPerSec);
            } else {
                activities.put(sec, 1);
            }

            user=logEntry.getAddress();
            if (userVisits.containsKey(user)) {
                visitsPerUser = userVisits.get(user);
                userVisits.put(user, ++visitsPerUser);
            } else {
                userVisits.put(user, 1);
            }
        }

        domains.add(logEntry.getDomain());
    }

    public double getTrafficRate(){
        try {
            return totalTraffic/Duration.between(minTime,maxTime).toHours();
        } catch (Exception e) {
            throw new ArithmeticException("Заданный интервал должен быть более одного часа");
        }
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    //список существующих страниц (с кодом ответа 200) сайта
    public HashSet<String> getExistingPages() {
        return existingPages;
    }

    //список несуществующих страниц (с кодом ответа 404) сайта
    public HashSet<String> getNonExistingPages() {
        return nonExistingPages;
    }

    //возвращает статистику операционных систем пользователей сайта
    public HashMap<String, Double> getOsParts(){
        double total=0;
        double cnt=0;
        double part=0;
        HashMap<String, Double> res=new HashMap<>();
        for (String key:opsysStats.keySet()) {
            total+=opsysStats.get(key);
        }
//        System.out.println("total "+total);
        for (String key:opsysStats.keySet()) {
            cnt=opsysStats.get(key);
            part = cnt/total;
            res.put(key,part);
        }
       return res;
    }
    //возвращает статистику браузеров сайта
    public HashMap<String, Double> getBrowserParts(){
        double total=0;
        double cnt=0;
        double part=0;
        HashMap<String, Double> res=new HashMap<>();
        for (String key:browserStats.keySet()) {
            total+=browserStats.get(key);
        }
//        System.out.println("total "+total);
        for (String key:browserStats.keySet()) {
            cnt=browserStats.get(key);
            part = cnt/total;
            res.put(key,part);
        }
       return res;
    }

    // Метод подсчёта среднего количества посещений сайта за час
    //Разделите период времени в часах, за который имеются записи в логе, на количество посещений пользователей
    public double averageNumberVisits(){
        double visits=lines.stream().filter(rec->!rec.getUserAgent().isBot()).count();
        double hours=getHours();
        return hours/visits;
    }

    //Метод подсчёта среднего количества ошибочных запросов в час
    //Разделите период времени в часах, за который имеются записи в логе, на количество запросов, по которым был ошибочный код ответа (4xx или 5xx)
    public double avarageNumberOfBadRequests(){
        double badRequests=lines.stream().filter(rec-> rec.getResponseCode()>=400 && rec.getResponseCode()<600).count();
        double hours=getHours();
        return hours/badRequests;
    }

    // Метод расчёта средней посещаемости одним пользователем
    //разделить общее количество посещений реальными пользователями (не ботами) на число уникальных IP-адресов таких пользователей
    public double averageUserActivity(){
        double visits=lines.stream().filter(rec->!rec.getUserAgent().isBot()).count(); //общее количество посещений реальными пользователями (не ботами)
        double numberUniqIp=lines.stream().map(rec->rec.getAddress()).distinct().count(); //число уникальных IP-адресов таких пользователей
        return visits/numberUniqIp;
    }

    //Метод расчёта пиковой посещаемости сайта (в секунду)
    public Map.Entry<Integer, Integer> peekSecondActivity(){
        Map.Entry<Integer, Integer> result=activities.entrySet().stream().max((e1,e2)->e1.getValue().compareTo(e2.getValue())).get();
        return result;
    }

    //список сайтов, со страниц которых есть ссылки на текущий сайт
    public HashSet<String> getDomains() {
        return domains;
    }

    //Метод расчёта максимальной посещаемости одним пользователем
    public Map.Entry<String, Integer> peekUserActivity(){
        Map.Entry<String, Integer> result=userVisits.entrySet().stream().max((e1,e2)->e1.getValue().compareTo(e2.getValue())).get();
        return result;
    }
}
