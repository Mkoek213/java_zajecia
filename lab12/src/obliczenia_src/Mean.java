package obliczenia_src;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mean {
    static double[] array;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<Double>(100);
    static void initArray(int size){
        array = new double[size];
        for(int i=0;i<size;i++){
            array[i]= Math.random()*size/(i+1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        initArray(100000000);
        System.out.println("\nParallel version 1 START ><><><><><><><<><><><><>><><><><><><><><><><><\n");
        parallelMean(2);
        parallelMean(4);
        parallelMean(8);
        parallelMean(16);
        System.out.println("\nParallel version 1 END ><><><><><><><<><><><><>><<><>><><><><><><><><><><\n");
        System.out.println("\nParallel version 2 START ><><><><><><><<><><><><><><><><><><><><><><><><><>\n");
        parallelMeanv2(2);
        parallelMeanv2(4);
        parallelMeanv2(8);
        parallelMeanv2(16);
        System.out.println("\nParallel version 2 END ><><><><><><><<><><><><<><><><><><><><><><><><><><><>\n");
        System.out.println("\nParallel version 3 START ><><><><><><><<><><><><>><<>><><><><><><><><\n");
        parallelMeanv3(2);
        parallelMeanv3(4);
        parallelMeanv3(8);
        parallelMeanv3(16);
        System.out.println("\nParallel version 3 END ><><><><><><><<><><><><>><<>><><><><><><><><\n");

    }


    static class MeanCalc extends Thread {
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            // ??? liczymy średnią
            // Wątek MeanCalc otrzyma w konstruktorze dwa indeksy: start i end i obliczy średnią dla elementów tablicy array pomiędzy start(włącznie) i end (wyłącznie). Zakładając, że tablicę podzielimy na bloki tej samej długości i każdy z nich będzie obsłużony przez jeden wątek, będzie mozna następnie obliczyć średnią z wartości zwróconych przez poszczególne wątki
            double sum = 0;
            int count = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
                count++;
            }
            if (count > 0) {
                mean = sum / count;
            }
            try {
                results.put(mean);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.printf(Locale.US, "%d-%d mean=%f\n", start, end, mean);
        }
    }

    static void parallelMean(int cnt) throws InterruptedException {
        /**
         * Oblicza średnią wartości elementów tablicy array uruchamiając równolegle działające wątki.
         * Wypisuje czasy operacji
         * @param cnt - liczba wątków
         */
        // utwórz tablicę wątków
        MeanCalc threads[]=new MeanCalc[cnt];
        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        int blockSize = array.length / cnt;
        // załóż, że array.length dzieli się przez cnt)
        double t1 = System.nanoTime()/1e6;
        //uruchom wątki
        for (int i = 0; i < cnt; i++) {
            int start = i * blockSize;
            int end = (i == cnt - 1) ? array.length : start + blockSize;
            threads[i] = new MeanCalc(start, end);
            threads[i].start();
        }
        double t2 = System.nanoTime()/1e6;
        // czekaj na ich zakończenie używając metody ''join''
        double overallMean = 0;
        for(MeanCalc mc:threads) {
            mc.join();
            overallMean += mc.mean;
        }

        overallMean /= cnt;
        // oblicz średnią ze średnich
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                overallMean);
    }

    static void parallelMeanv2(int cnt) throws InterruptedException {
        // utwórz tablicę wątków
        MeanCalc threads[]=new MeanCalc[cnt];

        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        int blockSize = array.length / cnt;

        // załóż, że array.length dzieli się przez cnt)
        double t1 = System.nanoTime()/1e6;

        //uruchom wątki
        for (int i = 0; i < cnt; i++) {
            int start = i * blockSize;
            int end = (i == cnt - 1) ? array.length : start + blockSize;
            threads[i] = new MeanCalc(start, end);
            threads[i].start();
        }

        // czekaj na ich zakończenie używając metody ''join''
        double overallMean = 0;
        try {
            for (int i = 0; i < cnt; i++) {
                overallMean += results.take(); // Blocking operation if the queue is empty
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        overallMean /= cnt;
        // oblicz średnią ze średnich
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US, "size = %d cnt = %d > t3-t1 = %f ms mean = %f\n",
                array.length,
                cnt,
                t3 - t1,
                overallMean);
    }

    static void parallelMeanv3(int cnt) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(cnt);

        // utwórz tablicę wątków
        MeanCalc threads[]=new MeanCalc[cnt];

        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        int blockSize = array.length / cnt;

        // załóż, że array.length dzieli się przez cnt)
        double t1 = System.nanoTime()/1e6;

        //uruchom wątki
        for (int i = 0; i < cnt; i++) {
            int start = i * blockSize;
            int end = (i == cnt - 1) ? array.length : start + blockSize;
            threads[i] = new MeanCalc(start, end);
            executor.execute(threads[i]);
        }

        // czekaj na ich zakończenie używając metody ''join''
        double overallMean = 0;
        try {
            for (int i = 0; i < cnt; i++) {
                overallMean += results.take(); // Blocking operation if the queue is empty
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        overallMean /= cnt;
        // oblicz średnią ze średnich
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US, "size = %d cnt = %d > t3-t1 = %f ms mean = %f\n",
                array.length,
                cnt,
                t3 - t1,
                overallMean);
    }
}