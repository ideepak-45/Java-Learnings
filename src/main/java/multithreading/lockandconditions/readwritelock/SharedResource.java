package main.java.multithreading.lockandconditions.readwritelock;

import java.util.concurrent.locks.ReadWriteLock;

public class SharedResource {
    private int resource;
    private final ReadWriteLock lock;

    public SharedResource(int initialValue, ReadWriteLock lock) {
        this.resource = initialValue;
        this.lock = lock;
    }

    public void increment() {
        lock.writeLock().lock();
        try {
            Thread.sleep(5000); // Simulate some work
            resource++;
            System.out.println(Thread.currentThread().getName() + " incremented resource to: " + resource);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void decrement() {
        lock.writeLock().lock();
        try {
            Thread.sleep(5000); // Simulate some work
            resource--;
            System.out.println(Thread.currentThread().getName() + " decremented resource to: " + resource);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int read() {
        lock.readLock().lock();
        try {
            Thread.sleep(5000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.readLock().unlock();
        }
        System.out.println(Thread.currentThread().getName() + " read resource value: " + resource);
        return resource;
    }
}
