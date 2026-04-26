package main.java.multithreading.threadpoolexecutor;

import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {

        // 0. Define a Custom Handler
        RejectedExecutionHandler customHandler = (Runnable task, ThreadPoolExecutor executor) -> {
            System.err.println("ALERT: Task rejected! Pool is full. " +
                               "Active Threads: " + executor.getActiveCount() +
                               " Queue Size: " + executor.getQueue().size());
            // You could save this task to a database here for later processing
        };

        // 1. Define the parameters
        int corePoolSize = 2;    // Always keep 2 threads alive
        int maxPoolSize = 5;     // Scale up to 5 if the queue gets full
        long keepAliveTime = 5000; // Extra threads die after 5 seconds of idling
        
        // 2. Create the Executor
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(3), // Queue can hold 3 tasks
                customHandler
        );

        executor.allowCoreThreadTimeOut(true); // Allow core threads to time out

        // 3. Submit tasks
        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            try {
                executor.submit(() -> {
                    System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000); // Simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            } catch (RejectedExecutionException e) {
                System.err.println("Error occurred while submitting task " + taskId);
            }
            
        }

        // 4. Shut down when finished
        executor.shutdown();
    }
}
