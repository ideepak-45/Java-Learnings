package main.java.multithreading.lockandconditions.monitorlock;

public class SharedResource {
    private int resource;

    public SharedResource(int initialValue) {
        this.resource = initialValue;
    }

    public synchronized void increment() {
        resource++;
    }

    public synchronized void decrement() {
        resource--;
    }

    public synchronized int getResource() {
        return resource;
    }
}
