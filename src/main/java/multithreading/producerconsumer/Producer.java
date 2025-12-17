package main.java.multithreading.producerconsumer;

public class Producer {
    SharedResource sharedResource;

    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void produce(int numberOfItems) {
        try {
            Thread.sleep(1000);
            for (int i = 0; i < numberOfItems; i++) {
                try {
                    sharedResource.addItemToBuffer(i);
                    System.out.println("Produced: " + i);
                } catch (Exception e) {
                    System.out.println("Producer encountered an error while producing item " + i + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Producer encountered an error: " + e.getMessage());
        }
    }
}
