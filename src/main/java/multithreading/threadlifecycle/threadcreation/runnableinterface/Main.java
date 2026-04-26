package main.java.multithreading.threadlifecycle.threadcreation.runnableinterface;

public class Main {
    public static void main(String[] args) {

        System.out.println("Process started with " + Thread.currentThread().getName());

        MultithreadingLearning runnableTask = new MultithreadingLearning();

        Thread thread1 = new Thread(runnableTask, "Thread-1");
        System.out.println(thread1.getName() + " created to execute runnable task.");
        System.out.println(thread1.getName() + " state before start(): " + thread1.getState());

        thread1.start();
        System.out.println(thread1.getName() + " state after start(): " + thread1.getState());

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread1.getName() + " state after task completion: " + thread1.getState());

        System.out.println("Process ended with " + Thread.currentThread().getName());
    }
}
