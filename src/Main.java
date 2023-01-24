import java.io.*;
import java.time.*;

public class Main {
    public static void main(String[] args) {
        calculteStats();
    }

    static void calculteStats(){
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

//                if (logRecord.getDateRec().toLocalTime().compareTo(start)>0 &&
//                        logRecord.getDateRec().toLocalTime().compareTo(end)<0)

                stats.addEntry(logRecord);
            }
            System.out.println("Общее количество строк в файле: "+numberOfLines);
            System.out.println("Общий трафик за заданный период "+stats.getTotalTraffic());
            System.out.println("Миним время "+stats.getMinTime());
            System.out.println("Макс время "+stats.getMaxTime());
            System.out.println("Разница в часах "+ stats.getHours());

            System.out.println(String.format("Средний объем часового трафика %f",stats.getTrafficRate()));

            System.out.println("Cтатистика операционных систем:\n"+stats.getOpsysStats());
            System.out.println("Доля операционных систем:\n"+stats.getOsParts());

            System.out.println("Cтатистика браузеров:\n"+stats.getBrowserStats());
            System.out.println("Доля браузеров:\n"+stats.getBrowserParts());

            System.out.println("Cреднее количество посещений сайта за час:\n"+stats.averageNumberVisits());
            System.out.println("Cреднее количество ошибочных запросов в час:\n"+stats.avarageNumberOfBadRequests());
            System.out.println("Cредняя посещаемость одним пользователем:\n"+stats.averageUserActivity());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ArithmeticException e2) {
            e2.printStackTrace();
        }
    }
}
