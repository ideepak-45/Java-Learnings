package main.java.multithreading.lockandconditions.stampedlock;

import java.util.concurrent.locks.StampedLock;

public class Main {
    public static void main(String[] args) {
        StampedLock lock = new StampedLock();
        SharedResource sharedResource = new SharedResource(0, lock);
        Thread writerThread = new Thread(() -> sharedResource.increment(), "WriterThread-1");
        Thread readerThread1A = new Thread(() -> sharedResource.read(), "ReaderThread-1A");
        Thread readerThread1B = new Thread(() -> sharedResource.read(), "ReaderThread-1B");
        writerThread.start();
        readerThread1A.start();
        readerThread1B.start();

        SharedResource sharedResource2 = new SharedResource(0, lock);
        Thread writerThread2 = new Thread(() -> sharedResource2.increment(), "WriterThread-2");
        Thread readerThread2 = new Thread(() -> sharedResource2.read(), "ReaderThread-2");

        writerThread2.start();
        readerThread2.start();
    }   
}
