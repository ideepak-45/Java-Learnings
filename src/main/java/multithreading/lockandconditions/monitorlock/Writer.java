package main.java.multithreading.lockandconditions.monitorlock;

public class Writer implements Runnable {
    private final SharedResource sharedResource;

    public Writer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void writeResource(boolean increment) {
        synchronized (sharedResource) {
            if (increment) {
                sharedResource.increment();
                System.out.println(Thread.currentThread().getName() + " Incremented resource.");
            } else {
                sharedResource.decrement();
                System.out.println(Thread.currentThread().getName() + " Decremented resource.");
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            writeResource(i % 2 == 0);
            try {
                Thread.sleep(1000); // Simulate time taken to write
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
