package zegar_src;

import java.time.LocalTime;

public class Clock extends Thread{

    @Override
    public void run() {
//        dodaj pętlę nieskończoną
//        w pętli odczytuj i drukuj bieżący czas
//        LocalTime time = LocalTime.now();
//        System.out.printf("%02d:%02d:%02d\n",
//                time.getHour(),
//                time.getMinute(),
//                time.getSecond());
//        Prawdopodobnie ten sam czas drukuje się wielokrotnie. Uśpij wątek na jedną sekundę (1000 milisekund) wprowadzając wywołanie metody sleep()


    }

    public static void main(String[] args) {
        while (true) {
            LocalTime time = LocalTime.now();
            System.out.printf("%02d:%02d:%02d\n",
                    time.getHour(),
                    time.getMinute(),
                    time.getSecond());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}