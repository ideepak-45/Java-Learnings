package main.java.multithreading.lockandconditions.monitorlock;

public class Reader implements Runnable {
    private final SharedResource sharedResource;

    public Reader(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void readResource() {
        synchronized (sharedResource) {
            int value = sharedResource.getResource();
            System.out.println(Thread.currentThread().getName() +" read resource value: " + value);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            readResource();
            try {
                Thread.sleep(1000); // Simulate time taken to read
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
