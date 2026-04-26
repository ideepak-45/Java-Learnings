package main.java.multithreading.threadlifecycle.threadcreation.threadclass;

public class MultithreadingLearning extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " state while task execution: " + Thread.currentThread().getState());
        System.out.println("Task executed by: " + Thread.currentThread().getName());
    }
}
