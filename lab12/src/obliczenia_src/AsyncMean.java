package obliczenia_src;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class AsyncMean {
    static double[] array;

    static void initArray(int size) {
        array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = Math.random() * size / (i + 1);
        }
    }

    static class MeanCalcSupplier implements Supplier<Double> {
        //...
        private final int start;
        private final int end;

        MeanCalcSupplier(int start, int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public Double get() {
            double sum = 0;
            int count = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
                count++;
            }
            double mean = (count > 0) ? sum / count : 0;
            System.out.printf(Locale.US, "%d-%d mean=%f\n", start, end, mean);
            return mean;
        }

        public static void asyncMeanv1() {
            int size = 100_000_000;
            initArray(size);

            // Number of partitions (number of tasks)
            int n = 16;
            double t1 = System.nanoTime()/1e6;
            // ExecutorService with a fixed thread pool
            ExecutorService executor = Executors.newFixedThreadPool(n);

            // Partition the array into blocks
            int blockSize = size / n;

            // List to store CompletableFuture instances
            List<CompletableFuture<Double>> partialResults = new ArrayList<>();

            // Create tasks and submit them to the executor
            for (int i = 0; i < n; i++) {
                int start = i * blockSize;
                int end = (i == n - 1) ? size : start + blockSize;
                CompletableFuture<Double> partialMean = CompletableFuture.supplyAsync(
                        new MeanCalcSupplier(start, end), executor);
                partialResults.add(partialMean);
            }

            // Aggregate results using join()
            double totalMean = 0;
            for (var pr : partialResults) {
                totalMean += pr.join(); // Join blocks the calling thread until the result is available
            }

            // Calculate the final mean
            double mean = totalMean / n;
            double t3 = System.nanoTime()/1e6;
            System.out.printf(Locale.US, "size=%d cnt=%d > t3-t1=%f ms mean=%f\n", size, n, t3 - t1, mean);

            // Shutdown the executor
            executor.shutdown();
        }

        static void asyncMeanv2() {
            int size = 100_000_000;
            initArray(size);
            ExecutorService executor = Executors.newFixedThreadPool(16);
            int n=10;

            BlockingQueue<Double> queue = new ArrayBlockingQueue<>(n);
            double t1 = System.nanoTime()/1e6;
            for (int i = 0; i < n; i++) {
                CompletableFuture.supplyAsync(
                        new MeanCalcSupplier(i * size / n, (i + 1) * size / n), executor)
            .thenApply(d -> queue.offer(d));
            }

            double mean=0;
            // w pętli obejmującej n iteracji wywołaj queue.take() i oblicz średnią
            for (int i = 0; i < n; i++) {
                try {
                    mean += queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mean /= n;
            double t3 = System.nanoTime()/1e6;
            System.out.printf(Locale.US,"size=%d cnt=%d > t3-t1=%f ms mean=%f\n", size, n, t3 - t1, mean);

            executor.shutdown();
        }
    }
    public static void main(String[] args) {
        MeanCalcSupplier.asyncMeanv1();
        System.out.println("\n<><><><><><><><><><><><><><><><><><><><><>\n");
        MeanCalcSupplier.asyncMeanv2();
    }
}