import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
                LogRecord logRecord=LogRecord.of(line);
                if (logRecord.isYandexBot())
                    yandexBots++;
                if (logRecord.isGoogleBot())
                    googleBots++;
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
