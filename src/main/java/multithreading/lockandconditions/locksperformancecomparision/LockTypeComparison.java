package main.java.multithreading.lockandconditions.locksperformancecomparision;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

public class LockTypeComparison {

    static final int THREADS = 8;
    static final int OPERATIONS = 1_000_000;

    static class SharedData {
        double value = 0;
    }

    // 1. synchronized
    static long testSynchronized() throws InterruptedException {
        SharedData data = new SharedData();
        Object lock = new Object();
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        long start = System.nanoTime();
        for (int i = 0; i < THREADS; i++) {
            executor.submit(() -> {
                Random rand = new Random();
                for (int j = 0; j < OPERATIONS / THREADS; j++) {
                    if (rand.nextInt(10) == 0) { // 10% writes
                        synchronized (lock) {
                            data.value += 1;
                        }
                    } else { // 90% reads
                        synchronized (lock) {
                            double v = data.value;
                        }
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return System.nanoTime() - start;
    }

    // 2. ReentrantLock
    static long testReentrantLock() throws InterruptedException {
        SharedData data = new SharedData();
        Lock lock = new ReentrantLock();
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        long start = System.nanoTime();
        for (int i = 0; i < THREADS; i++) {
            executor.submit(() -> {
                Random rand = new Random();
                for (int j = 0; j < OPERATIONS / THREADS; j++) {
                    if (rand.nextInt(10) == 0) {
                        lock.lock();
                        try {
                            data.value += 1;
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        lock.lock();
                        try {
                            double v = data.value;
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return System.nanoTime() - start;
    }

    // 3. Semaphore (binary)
    static long testSemaphore() throws InterruptedException {
        SharedData data = new SharedData();
        Semaphore sem = new Semaphore(1);
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        long start = System.nanoTime();
        for (int i = 0; i < THREADS; i++) {
            executor.submit(() -> {
                Random rand = new Random();
                for (int j = 0; j < OPERATIONS / THREADS; j++) {
                    try {
                        sem.acquire();
                        if (rand.nextInt(10) == 0) {
                            data.value += 1;
                        } else {
                            double v = data.value;
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        sem.release();
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return System.nanoTime() - start;
    }

    // 4. ReentrantReadWriteLock
    static long testReentrantReadWriteLock() throws InterruptedException {
        SharedData data = new SharedData();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        long start = System.nanoTime();
        for (int i = 0; i < THREADS; i++) {
            executor.submit(() -> {
                Random rand = new Random();
                for (int j = 0; j < OPERATIONS / THREADS; j++) {
                    if (rand.nextInt(10) == 0) {
                        lock.writeLock().lock();
                        try {
                            data.value += 1;
                        } finally {
                            lock.writeLock().unlock();
                        }
                    } else {
                        lock.readLock().lock();
                        try {
                            double v = data.value;
                        } finally {
                            lock.readLock().unlock();
                        }
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return System.nanoTime() - start;
    }

    // 5. StampedLock (optimistic reads)
    static long testStampedLock() throws InterruptedException {
        SharedData data = new SharedData();
        StampedLock lock = new StampedLock();
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        long start = System.nanoTime();
        for (int i = 0; i < THREADS; i++) {
            executor.submit(() -> {
                Random rand = new Random();
                for (int j = 0; j < OPERATIONS / THREADS; j++) {
                    if (rand.nextInt(10) == 0) {
                        long stamp = lock.writeLock();
                        try {
                            data.value += 1;
                        } finally {
                            lock.unlockWrite(stamp);
                        }
                    } else {
                        long stamp = lock.tryOptimisticRead();
                        double v = data.value;
                        if (!lock.validate(stamp)) {
                            stamp = lock.readLock();
                            try {
                                v = data.value;
                            } finally {
                                lock.unlockRead(stamp);
                            }
                        }
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return System.nanoTime() - start;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Running lock performance comparison...");

        System.out.printf("synchronized: %.2f ms%n", testSynchronized() / 1_000_000.0);
        System.out.printf("ReentrantLock: %.2f ms%n", testReentrantLock() / 1_000_000.0);
        System.out.printf("Semaphore: %.2f ms%n", testSemaphore() / 1_000_000.0);
        System.out.printf("ReentrantReadWriteLock: %.2f ms%n", testReentrantReadWriteLock() / 1_000_000.0);
        System.out.printf("StampedLock (Optimistic): %.2f ms%n", testStampedLock() / 1_000_000.0);
    }
}

