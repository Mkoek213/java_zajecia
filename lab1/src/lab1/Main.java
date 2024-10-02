package lab1;

import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int a = 5, b = 7;
        String c = String.format("%.2f", a + b + 0.11111);
        System.out.printf("Result is %s\n", c);
        System.out.println(c.getClass().getName());
    }
}
