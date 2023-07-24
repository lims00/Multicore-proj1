package problem1;

import java.util.*;

public class Pc_dynamic {
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 1;
    private static int BLOCK_SIZE;
    private static int[] primeCounters;
    private static int currentIndex = 0;

    public static void main(String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }

        BLOCK_SIZE = 10;
        primeCounters = new int[NUM_THREADS];

        PrimeCounterThread[] threads = new PrimeCounterThread[NUM_THREADS];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new PrimeCounterThread(i);
            threads[i].start();
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int totalCounter = 0;
        for (int i = 0; i < NUM_THREADS; i++) {
            totalCounter += primeCounters[i];
        }

        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.println("Program Execution Time: " + timeDiff + "ms");
        System.out.println("1..." + (NUM_END - 1) + " prime# counters: " + totalCounter);
    }

    private static boolean isPrime(int x) {
        int i;
        if (x<=1) return false;
        for (i=2;i<x;i++) {
            if (x%i == 0) return false;
        }
        return true;
    }

    private static class PrimeCounterThread extends Thread {
        private int threadIndex;

        public PrimeCounterThread(int threadIndex) {
            this.threadIndex = threadIndex;
        }

        public void run() {
            long startThreadTime = System.currentTimeMillis();
            while (true) {
                int currentIndexCopy;
                synchronized (primeCounters) {
                    currentIndexCopy = currentIndex;
                    currentIndex += BLOCK_SIZE;
                }
                if (currentIndexCopy >= NUM_END) {
                    break;
                }
                int endIndex = currentIndexCopy + BLOCK_SIZE;
                for (int j = currentIndexCopy; j < endIndex; j++) {
                    if (j >= NUM_END) {
                        break;
                    }
                    if (isPrime(j)) {
                        synchronized (primeCounters) {
                            primeCounters[threadIndex]++;
                        }
                    }
                }
            }
            long endThreadTime = System.currentTimeMillis();
            System.out.println("Thread " + threadIndex + " execution time: " + (endThreadTime - startThreadTime) + "ms");
        }
    }
    
}
