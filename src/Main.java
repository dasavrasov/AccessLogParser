import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
//        String strDate="25/Sep/2022:06:45:11 +0300";
//        String strDate="25/Sep/2022:06:45:11";
//        String strDate="[25/Sep/2022]";
//        strDate = strDate.substring(1,strDate.length()-1);
//        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.ENGLISH);
//        strDate=LocalDateTime.now().format(dtf);

//        DateTimeFormatter dtf1=new DateTimeFormatterBuilder().parseCaseInsensitive()
//                .appendPattern("dd/MMM/yyyy")
//                .toFormatter().withLocale(Locale.ENGLISH);

//        LocalDateTime dateTime=LocalDateTime.parse(strDate,dtf);
//        System.out.println(strDate);
//

//        System.out.println(dateTime);

//        LocalDateTime dateTime=
        File path=new File("access.log");
        int numberOfLines=0;
        int yandexBots=0;
        int googleBots=0;

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
                System.out.println(logRecord);
                if (logRecord.isYandexBot())
                    yandexBots++;
                if (logRecord.isGoogleBot())
                    googleBots++;
//                if (numberOfLines>25)
//                    System.exit(0);
            }
            System.out.println("yandexBots "+yandexBots);
            System.out.println("googleBots "+googleBots);
            System.out.println("googleBots "+numberOfLines);
            System.out.println("общее количество строк в файле: "+numberOfLines);

            double yb=(double)yandexBots/(double)numberOfLines;
            double gb=(double)googleBots/(double)numberOfLines;

            System.out.println("доля запросов от YandexBot в файле: "+yb);
            System.out.println("доля запросов от GoogleBot в файле: "+gb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ArithmeticException e2) {
            e2.printStackTrace();
        }
    }
}
