package main.java.multithreading.lockandconditions.readwritelock;

public class Reader implements Runnable {
    private final SharedResource sharedResource;

    public Reader(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int value = sharedResource.read();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
