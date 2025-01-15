package csvreader;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Zad1 {
    static double[] array;
    static BlockingQueue<double[]> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        int size = 100_000_000;
        array = new double[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = -1e3 + random.nextDouble() * 2e3;
        }

        // test Max3Thread
        Max3Thread max3Thread = new Max3Thread(0, 10);
        max3Thread.start();
        try {
            max3Thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        double[] maxValues = queue.poll();
        System.out.println(Arrays.toString(maxValues));

        // Eksperymenty
        System.out.println("\n<><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        System.out.println("Porównanie czasów wykonania implementacji sekwencyjnej i równoległej dla cnt=4");
        double t1 = System.nanoTime() / 1e6;
        double[] result_seq = sequentialMax3();
        double t2 = System.nanoTime() / 1e6;
        System.out.printf("Sequential version time: t2-t1=%f ms\n", t2 - t1);
        System.out.println("Sequential result: " + Arrays.toString(result_seq));
        double t3 = System.nanoTime() / 1e6;
        double[] result_par = parallelMax3(4);
        double t4 = System.nanoTime() / 1e6;
        System.out.printf("Parallel version: time: t4-t3=%f ms\n", t4 - t3);
        System.out.println("Parallel result: " + Arrays.toString(result_par));
        System.out.println("\n");

    }

    public static double[] sequentialMax3() {
        double[] maxValues = {-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE};
        for (double value : array) {
            insertMax(maxValues, value);
        }
        return maxValues;
    }

    static class Max3Thread extends Thread {
        int start, end;

        Max3Thread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            double[] local_max = {-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE};
            for (int i = start; i < end; i++) {
                insertMax(local_max, array[i]);
            }
            try {
                queue.put(local_max);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            queue.add(local_max);
        }
    }

    public static double[] parallelMax3(int cnt) throws InterruptedException {
        int chunkSize = array.length / cnt;
        Thread[] threads = new Thread[cnt];
        for (int i = 0; i < cnt; i++) {
            int start = i * chunkSize;
            int end = (i == cnt - 1) ? array.length : start + chunkSize;
            threads[i] = new Max3Thread(start, end);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        double[] local_max = {-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE};
        while (!queue.isEmpty()) {
            double[] localMax = queue.take();
            for (double value : localMax) {
                insertMax(local_max, value);
            }
        }
        return local_max;
    }


    private static void insertMax(double[] maxValues, double value) {
        if (value > maxValues[2]) {
            maxValues[0] = maxValues[1];
            maxValues[1] = maxValues[2];
            maxValues[2] = value;
        } else if (value > maxValues[1]) {
            maxValues[0] = maxValues[1];
            maxValues[1] = value;
        } else if (value > maxValues[0]) {
            maxValues[0] = value;
        }
    }
}
