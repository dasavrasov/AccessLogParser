import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        LocalTime start=LocalTime.of(06,0);
        LocalTime end=LocalTime.of(07,1);
        calculteStats(start,end);
    }

    static void calculteStats(LocalTime start, LocalTime end){
        File path=new File("access.log");
        int numberOfLines=0;

        Statistics stats=new Statistics();

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(path);
            BufferedReader reader =
                    new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();
                if (length>1024)
                    throw new TooLongStringException("в файле встретилась строка длиннее 1024 символов");
                numberOfLines++;
                LogEntry logRecord= new LogEntry(line);

                if (logRecord.getDateRec().toLocalTime().compareTo(start)>0 &&
                        logRecord.getDateRec().toLocalTime().compareTo(end)<0)
                    stats.addEntry(logRecord);
            }
            System.out.println("Общее количество строк в файле: "+numberOfLines);
            System.out.println("Общий трафик за заданный период "+stats.getTotalTraffic());
            System.out.println("Миним время "+stats.getMinTime());
            System.out.println("Макс время "+stats.getMaxTime());
            System.out.println("Разница в часах "+ Duration.between(stats.getMinTime(),stats.getMaxTime()).toHours());

            System.out.println(String.format("Средний объем часового трафика %f",stats.getTrafficRate()));

            System.out.println("Cтатистика операционных систем:\n"+stats.getOpsysStats());
            System.out.println("Доля операционных систем:\n"+stats.getOsStats());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ArithmeticException e2) {
            e2.printStackTrace();
        }
    }
}
