package main.java.multithreading.threadlifecycle.threadcreation.runnableinterface;

public class MultithreadingLearning implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " state while task execution: " + Thread.currentThread().getState());
        System.out.println("Task executed by: " + Thread.currentThread().getName());
    }   
}
