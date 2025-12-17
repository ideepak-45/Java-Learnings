package main.java.multithreading.producerconsumer;

public class Main {
    public static void main(String[] args) {
        System.out.println("_____________ Producer-Consumer Problem _____________");

        SharedResource sharedResource = new SharedResource(5);

        Producer producer = new Producer(sharedResource);
        Consumer consumer = new Consumer(sharedResource);

        Thread producerThread = new Thread(() -> producer.produce(10));
        Thread consumerThread = new Thread(() -> consumer.consume(10));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
