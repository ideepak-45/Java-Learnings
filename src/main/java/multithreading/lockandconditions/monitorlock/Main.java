package main.java.multithreading.lockandconditions.monitorlock;

public class Main {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource(0);
        Thread writerThread = new Thread(new Writer(sharedResource), "WriterThread-1");
        Thread readerThread = new Thread(new Reader(sharedResource), "ReaderThread-1");
        writerThread.start();
        readerThread.start();

        SharedResource sharedResource2 = new SharedResource(0);
        Thread writerThread2 = new Thread(new Writer(sharedResource2), "WriterThread-2");
        Thread readerThread2 = new Thread(new Reader(sharedResource2), "ReaderThread-2");
        writerThread2.start();
        readerThread2.start();
    }
}
