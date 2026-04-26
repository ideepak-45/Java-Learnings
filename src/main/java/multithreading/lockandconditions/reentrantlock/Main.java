package main.java.multithreading.lockandconditions.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;


public class Main {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        SharedResource sharedResource = new SharedResource(0, lock);
        Thread writerThread = new Thread(()->{sharedResource.increment();}, "WriterThread-1");
        Thread readerThread = new Thread(()->{sharedResource.read();}, "ReaderThread-1");
        writerThread.start();
        readerThread.start();

        SharedResource sharedResource2 = new SharedResource(0, lock);
        Thread writerThread2 = new Thread(()->{sharedResource2.increment();}, "WriterThread-2");
        Thread readerThread2 = new Thread(()->{sharedResource2.read();}, "ReaderThread-2");
        Thread readerThread3 = new Thread(()->{sharedResource2.read();}, "ReaderThread-3");
        writerThread2.start();
        readerThread2.start();
        readerThread3.start();
    }
}
