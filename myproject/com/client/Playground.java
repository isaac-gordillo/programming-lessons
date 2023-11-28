package myproject.com.client;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Playground {
    public static void main(String[] args) {
        int someNumberValue = requestIntegerInputToUser("please insert a number");

        System.out.println(someNumberValue);

    }

    private static int requestIntegerInputToUser(String message) {
        Integer insertedNumber = null;
        Scanner sc;

        System.out.println(message);
        while (true) {
            try {
                sc = new Scanner(System.in);
                insertedNumber = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("The entered value is not a valid number. Please provide a valid numerical input.");
            }
        }
        sc.close();
        return insertedNumber.intValue();
    }
}
