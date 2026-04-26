package main.java.multithreading.lockandconditions.semaphorelock;


import java.util.concurrent.Semaphore;



public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2, true); // Allow up to 2 concurrent accesses

        SharedResource sharedResource = new SharedResource(0, semaphore);
        Thread writerThread = new Thread(() -> sharedResource.increment(), "WriterThread-1");
        Thread readerThread1A = new Thread(() -> sharedResource.read(), "ReaderThread-1A");
        Thread readerThread1B = new Thread(() -> sharedResource.read(), "ReaderThread-1B");
        writerThread.start();
        readerThread1A.start();
        readerThread1B.start();

        SharedResource sharedResource2 = new SharedResource(0, semaphore);
        Thread writerThread2 = new Thread(() -> sharedResource2.increment(), "WriterThread-2");
        Thread readerThread2 = new Thread(() -> sharedResource2.read(), "ReaderThread-2");

        writerThread2.start();
        readerThread2.start();
    }
}
