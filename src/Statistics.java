import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private long totalTraffic=0;
    private LocalTime minTime=LocalTime.of(23,59,59);

    private LocalTime maxTime=LocalTime.of(0,0,0);


    private HashSet<String> pages=new HashSet<>();

    private HashMap<String, Integer> opsysStats=new HashMap<>();

    public HashMap<String, Integer> getOpsysStats() {
        return opsysStats;
    }

    public void addEntry(LogEntry logEntry){
        SysInfo sysRec;
        totalTraffic+=logEntry.getResponseSize();
        if (logEntry.getDateRec().toLocalTime().compareTo(minTime)<0)
            minTime=logEntry.getDateRec().toLocalTime();
        if (logEntry.getDateRec().toLocalTime().compareTo(maxTime)>0)
            maxTime=logEntry.getDateRec().toLocalTime();
        if (logEntry.getResponseCode()==200)
            pages.add(logEntry.getAddress());
        sysRec=logEntry.getUserAgent().getSys();
        if (!opsysStats.containsKey(sysRec.name()))
            opsysStats.put(sysRec.name(),1);
        else
            opsysStats.put(sysRec.name(),opsysStats.get(sysRec.name())+1);
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

    public LocalTime getMinTime() {
        return minTime;
    }

    public LocalTime getMaxTime() {
        return maxTime;
    }

    //адреса существующих страниц (с кодом ответа 200) сайта
    public HashSet<String> getPages() {
        return pages;
    }

    //возвращает статистику операционных систем пользователей сайта
    public HashMap<String, Double> getOsStats(){
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
}
