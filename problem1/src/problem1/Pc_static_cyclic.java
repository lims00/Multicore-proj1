package problem1;

import java.util.*;

public class Pc_static_cyclic extends Thread {
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 1;
    private static int[] primeCounters;

    private int threadIndex;

    public Pc_static_cyclic(int startIndex) {
        this.threadIndex = startIndex;
    }

    public void run() {
        int currentIndex = threadIndex*10;
        //System.out.println(currentIndex);
        long startThreadTime = System.currentTimeMillis();
        int step_size=10;
        while (currentIndex < NUM_END ) {
        	for (int i=1;i<=step_size;i++) {
        		if (currentIndex+i >=NUM_END) {
        			break;
        		}
        		if (isPrime(currentIndex+i)) {
        			synchronized (primeCounters) {
        				primeCounters[threadIndex]++;
        			}
        		}
        	}
        	currentIndex+=step_size*(NUM_THREADS);
        }
        long endThreadTime = System.currentTimeMillis();
        System.out.println("Thread " + threadIndex + " execution time: " + (endThreadTime - startThreadTime) + "ms");
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }

        primeCounters = new int[NUM_THREADS];

        Thread[] threads = new Thread[NUM_THREADS];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Pc_static_cyclic(i);
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
}