import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int firstNumber;
        int secondNumber;
        System.out.println("Введите первое число:");
        firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        secondNumber = new Scanner(System.in).nextInt();

        int plus = firstNumber + secondNumber;
        int minus = firstNumber - secondNumber;
        int mult = firstNumber * secondNumber;
        double divide = firstNumber / secondNumber;

        System.out.println("Сумма: " + plus);
        System.out.println("Разность: " + minus);
        System.out.println("Произведение: " + mult);
        System.out.println("Частное: " + divide);
    }
}
