package main.java.multithreading.threadlifecycle.threadcreation.threadclass;

public class Main {
    public static void main(String[] args) {

        System.out.println("Process started with " + Thread.currentThread().getName());

        MultithreadingLearning threadTask = new MultithreadingLearning();

        System.out.println(threadTask.getName() + " created to execute thread task.");
        System.out.println(threadTask.getName() + " state before start(): " + threadTask.getState());

        threadTask.start();
        System.out.println(threadTask.getName() + " state after start(): " + threadTask.getState());

        try {
            threadTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadTask.getName() + " state after task completion: " + threadTask.getState());

        System.out.println("Process ended with " + Thread.currentThread().getName());
    }
}
