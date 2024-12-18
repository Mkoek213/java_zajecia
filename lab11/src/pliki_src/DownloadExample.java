package pliki_src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadExample {
    static int count = 0;
    static AtomicInteger atomicCount = new AtomicInteger(0);
    static Semaphore sem = new Semaphore(0);

    static String[] toDownload = {
            "https://home.agh.edu.pl/~pszwed/wyklad-c/01-jezyk-c-intro.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/02-jezyk-c-podstawy-skladni.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/03-jezyk-c-instrukcje.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/04-jezyk-c-funkcje.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/05-jezyk-c-deklaracje-typy.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/06-jezyk-c-wskazniki.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/07-jezyk-c-operatory.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/08-jezyk-c-lancuchy-znakow.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/09-jezyk-c-struktura-programow.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/10-jezyk-c-dynamiczna-alokacja-pamieci.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/11-jezyk-c-biblioteka-we-wy.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/preprocesor-make-funkcje-biblioteczne.pdf"
    };

    static class Downloader implements Runnable {
        private final String url;

        Downloader(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            try (InputStream in = new URL(url).openStream();
                 FileOutputStream out = new FileOutputStream(fileName)) {
                int c;
                while ((c = in.read()) != -1) {
                    out.write(c);
                }
                System.out.println("Downloaded: " + fileName);
                synchronized (DownloadExample.class) {
                    count++;
                }
                atomicCount.incrementAndGet();
                sem.release();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void sequentialDownload() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Downloader(url).run(); // run directly in main thread
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "Sequential time: %f ms\n", t2 - t1);
    }

    static void concurrentDownload() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Thread(new Downloader(url)).start();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "Concurrent v1 time: %f ms\n", t2 - t1);
    }

    static void concurrentDownload2() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Thread(new Downloader(url)).start();
        }

        while (count != toDownload.length) {
            Thread.yield();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "Concurrent v2 time: %f ms\n", t2 - t1);
    }

    static void concurrentDownload2_5() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Thread(new Downloader(url)).start();
        }
        while (atomicCount.get() != toDownload.length) {
            Thread.yield();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "Concurrent v2.5 time: %f ms\n", t2 - t1);
    }


    static void concurrentDownload3() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Thread(new Downloader(url)).start();
        }
        try {
            sem.acquire(toDownload.length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "Concurrent v3 time: %f ms\n", t2 - t1);
    }

    public static void main(String[] args) {
//         sequentialDownload();
//         concurrentDownload();
//            concurrentDownload2();
//        concurrentDownload2_5();
        concurrentDownload3();
    }
}
