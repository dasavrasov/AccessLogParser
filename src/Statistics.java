import java.time.LocalTime;

public class Statistics {
    int totalTraffic=0;
    LocalTime minTime=LocalTime.of(23,59,59);
    LocalTime maxTime=LocalTime.of(0,0,0);

    public void addEntry(LogEntry logEntry){
        totalTraffic+=logEntry.getResponseSize();
        if (logEntry.getDateRec().toLocalTime().compareTo(minTime)<0)
            minTime=logEntry.getDateRec().toLocalTime();
        if (logEntry.getDateRec().toLocalTime().compareTo(maxTime)>0)
            maxTime=logEntry.getDateRec().toLocalTime();
    }

    public double getTrafficRate(){
        return (double)(maxTime.getHour()-minTime.getHour())/(double)totalTraffic;
    }
}
