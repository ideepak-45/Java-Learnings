package main.java.multithreading.lockandconditions.semaphorelock;

import java.util.concurrent.Semaphore;

public class SharedResource {
    private int resource;
    private final Semaphore semaphore;

    public SharedResource(int initialValue, Semaphore semaphore) {
        this.resource = initialValue;
        this.semaphore = semaphore;
    }

    public void increment() {
        try {
            semaphore.acquire();
            resource++;
            System.out.println(Thread.currentThread().getName() + " incremented resource to: " + resource);
            Thread.sleep(5000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public void decrement() {
        try {
            semaphore.acquire();
            resource--;
            System.out.println(Thread.currentThread().getName() + " decremented resource to: " + resource);
            Thread.sleep(5000); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public int read() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " read resource: " + resource);
            Thread.sleep(5000); // Simulate some work
            return resource;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        } finally {
            semaphore.release();
        }
    }

}
