package main.java.multithreading.lockfreeconcurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileVsAtomicVariables {

    // Volatile counter (visibility but NOT atomicity)
    private static volatile int volatileCounter = 0;

    // Atomic counter (visibility + atomicity)
    private static AtomicInteger atomicCounter = new AtomicInteger(0);

    // Number of threads and increments
    private static final int THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 100_000;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        // Test volatile counter
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    volatileCounter++; // Not atomic!
                }
            });
        }
        long startVolatile = System.nanoTime();
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        long endVolatile = System.nanoTime();

        System.out.println("Volatile Counter Expected: " + (THREADS * INCREMENTS_PER_THREAD));
        System.out.println("Volatile Counter Actual:   " + volatileCounter);
        System.out.printf("Volatile Time: %.2f ms%n%n", (endVolatile - startVolatile) / 1_000_000.0);

        // Reset threads for atomic counter test
        threads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    atomicCounter.incrementAndGet(); // Atomic operation
                }
            });
        }
        long startAtomic = System.nanoTime();
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        long endAtomic = System.nanoTime();

        System.out.println("Atomic Counter Expected: " + (THREADS * INCREMENTS_PER_THREAD));
        System.out.println("Atomic Counter Actual:   " + atomicCounter.get());
        System.out.printf("Atomic Time: %.2f ms%n", (endAtomic - startAtomic) / 1_000_000.0);
    }
}
