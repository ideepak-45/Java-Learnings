package main.java.multithreading.producerconsumer;

public class Consumer {
    SharedResource sharedResource;

    public Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void consume(int numberOfItems) {
        try {
            for (int i = 0; i < numberOfItems; i++) {
                try {
                    int item = sharedResource.removeItemFromBuffer();
                    System.out.println("Consumed: " + item);
                } catch (Exception e) {
                    System.out.println("Consumer encountered an error while consuming item " + i + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Consumer encountered an error: " + e.getMessage());
        }
    }
}
