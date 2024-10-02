package lab1;

import java.util.Scanner;

public class zad1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int len = input.nextInt();
        int A = 0;
        if (len %4 == 0){ A = 1;}
        int C = 0;
        if (len % 2 == 0){
            C = (len/2 -1 -A)/2;
        }
        System.out.printf("wynik: %d", (C));
    }

    public static int silnia(int idx) {
        int res = 1;
        for (int i = 1; i <= idx; i++) {
            res = res * i;
        }
        return res;
    }
}
