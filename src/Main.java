import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File path=new File("access.log");
        int numberOfLines=0;
        int longestLineLength=0;
        int shortestLineLength=Integer.MAX_VALUE;

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(path);
            BufferedReader reader =
                    new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();
                if (length>longestLineLength) {
                    longestLineLength=length;
                }
                if (longestLineLength>1024)
                    throw new TooLongStringException("в файле встретилась строка длиннее 1024 символов");
                if (length<shortestLineLength) {
                    shortestLineLength=length;
                }
                numberOfLines++;
            }
            System.out.println("общее количество строк в файле: "+numberOfLines);
            System.out.println("длина самой длинной строки в файле: "+longestLineLength);
            System.out.println("длина самой короткой строки в файле: "+shortestLineLength);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
