import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;

public class Statistics {
    private long totalTraffic=0;
    private LocalTime minTime=LocalTime.of(23,59,59);

    private LocalTime maxTime=LocalTime.of(0,0,0);

    public void addEntry(LogEntry logEntry){
        totalTraffic+=logEntry.getResponseSize();
        if (logEntry.getDateRec().toLocalTime().compareTo(minTime)<0)
            minTime=logEntry.getDateRec().toLocalTime();
        if (logEntry.getDateRec().toLocalTime().compareTo(maxTime)>0)
            maxTime=logEntry.getDateRec().toLocalTime();
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
}
