package main.java.multithreading.lockandconditions.stampedlock;

import java.util.concurrent.locks.StampedLock;

public class SharedResource {
    private int resource;
    private final StampedLock lock;

    public SharedResource(int initialValue, StampedLock lock) {
        this.resource = initialValue;
        this.lock = lock;
    }

    public void increment() {
        long stamp = lock.writeLock();
        try {
            Thread.sleep(5000); // Simulate some work
            resource++;
            System.out.println(Thread.currentThread().getName() + " incremented resource to: " + resource);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public void decrement() {
        long stamp = lock.writeLock();
        try {
            Thread.sleep(5000); // Simulate some work
            resource--;
            System.out.println(Thread.currentThread().getName() + " decremented resource to: " + resource);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public int read() {
        long stamp = lock.readLock();
        try {
            Thread.sleep(5000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlockRead(stamp);
        }
        System.out.println(Thread.currentThread().getName() + " read resource: " + resource);
        return resource;
    }

}
