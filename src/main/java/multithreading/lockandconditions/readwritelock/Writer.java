package main.java.multithreading.lockandconditions.readwritelock;

public class Writer implements Runnable {
    private final SharedResource sharedResource;

    public Writer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            sharedResource.increment();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
