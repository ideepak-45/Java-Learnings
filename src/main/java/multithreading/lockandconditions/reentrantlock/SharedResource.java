package main.java.multithreading.lockandconditions.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class SharedResource {
    private int resource;
    private final ReentrantLock lock;

    public SharedResource(int initialValue, ReentrantLock lock) {
        this.resource = initialValue;
        this.lock = lock;
    }

    public void increment() {
        lock.lock();
        try {
            Thread.sleep(5000); // Simulate some work
            resource++;
            System.out.println(Thread.currentThread().getName() + " Resource incremented to: " + resource);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            Thread.sleep(5000); // Simulate some work
            resource--;
            System.out.println(Thread.currentThread().getName() + " Resource decremented to: " + resource);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public int read() {
        lock.lock();
        try {
            Thread.sleep(5000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " Resource read: " + resource);
        return resource;
    }

}
