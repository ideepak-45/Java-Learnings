package main.java.multithreading.lockandconditions.readwritelock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) {
        ReadWriteLock lock = new ReentrantReadWriteLock();

        SharedResource sharedResource = new SharedResource(0, lock);
        Thread writerThread = new Thread(new Writer(sharedResource), "WriterThread-1");
        Thread readerThread1A = new Thread(new Reader(sharedResource), "ReaderThread-1A");
        Thread readerThread1B = new Thread(new Reader(sharedResource), "ReaderThread-1B");
        writerThread.start();
        readerThread1A.start();
        readerThread1B.start();


        SharedResource sharedResource2 = new SharedResource(0, lock);
        Thread writerThread2 = new Thread(new Writer(sharedResource2), "WriterThread-2");
        Thread readerThread2 = new Thread(new Reader(sharedResource2), "ReaderThread-2");

        writerThread2.start();
        readerThread2.start();
    }
}
