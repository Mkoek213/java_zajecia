package lab1;

import java.lang.reflect.Type;
import java.util.Scanner;

public class Fibo {
    public static void main(String[] args) {
        double[] table;
        table = new double[]{1.1, 2.2, 3.3, 4.4};
        System.out.println(table.length);
        Scanner input = new Scanner(System.in);
        int idx = input.nextInt();
        System.out.println(Fibonacci(idx));
    }

    public static int Fibonacci(int idx) {
        if (idx == 1 || idx == 0) {
            return idx;
        }else{
            return Fibonacci(idx - 1) + Fibonacci(idx - 2);
        }
    }
}
