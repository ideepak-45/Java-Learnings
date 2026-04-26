package main.java.multithreading.lockandconditions.stampedlockoptimisticread;

import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {

    static class SharedData {
        private double x, y; // Example shared state
        private final StampedLock lock = new StampedLock();

        // Write method (exclusive)
        public void move(double deltaX, double deltaY) {
            long stamp = lock.writeLock(); // Acquire write lock
            try {
                x += deltaX;
                y += deltaY;
                System.out.println(Thread.currentThread().getName() +
                        " moved to (" + x + ", " + y + ")");
            } finally {
                lock.unlockWrite(stamp); // Always release
            }
        }

        // Read method (shared)
        public double distanceFromOrigin() {
            long stamp = lock.readLock(); // Acquire read lock
            try {
                return Math.sqrt(x * x + y * y);
            } finally {
                lock.unlockRead(stamp);
            }
        }

        // Optimistic read (non-blocking)
        public double distanceFromOriginOptimistic() {
            long stamp = lock.tryOptimisticRead(); // Non-blocking read
            double currentX = x;
            double currentY = y;

            // Validate if no write occurred during read
            if (!lock.validate(stamp)) {
                // Fallback to read lock if data changed
                stamp = lock.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    lock.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }
    }

    public static void main(String[] args) {
        SharedData data = new SharedData();

        // Writer thread
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                data.move(Math.random(), Math.random());
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
        }, "Writer");

        // Reader thread (shared read lock)
        Thread reader = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                double dist = data.distanceFromOrigin();
                System.out.println(Thread.currentThread().getName() +
                        " read distance: " + dist);
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            }
        }, "Reader");

        // Optimistic reader thread
        Thread optimisticReader = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                double dist = data.distanceFromOriginOptimistic();
                System.out.println(Thread.currentThread().getName() +
                        " optimistic read distance: " + dist);
                try { Thread.sleep(150); } catch (InterruptedException ignored) {}
            }
        }, "OptimisticReader");

        writer.start();
        reader.start();
        optimisticReader.start();
    }
}
