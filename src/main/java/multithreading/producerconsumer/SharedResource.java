package main.java.multithreading.producerconsumer;

public class SharedResource {

    private class MyQueue<T> {
        private T[] queue;
        private int capacity;
        private int size;
        private int front;
        private int rear;

        public MyQueue(int capacity) {
            this.capacity = capacity;
            this.queue = (T[]) new Object[capacity];
            this.size = 0;
            this.front = 0;
            this.rear = 0;
        }

        public void enqueue(T item) throws Exception {
            if (size == capacity) {
                throw new Exception("Queue is full");
            }
            queue[rear] = item;
            rear = (rear + 1) % capacity;
            size++;
        }

        public T dequeue() throws Exception {
            if (size == 0) {
                throw new Exception("Queue is empty");
            }
            T item = queue[front];
            front = (front + 1) % capacity;
            size--;
            return item;
        }

        public int getSize() {
            return size;
        }

        public int getCapacity() {
            return capacity;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == capacity;
        }
    }

    private MyQueue<Integer> queueBuffer;

    public SharedResource(int capacity) {
        this.queueBuffer = new MyQueue<>(capacity);
    }

    synchronized public void addItemToBuffer(int item) throws Exception {
        while (queueBuffer.isFull()) {
            System.out.println("Buffer is full, producer is waiting...");
            wait();
        }   
        queueBuffer.enqueue(item);
        System.out.println("Added item to buffer: " + item);
        notifyAll();
    }

    synchronized public int removeItemFromBuffer() throws Exception {
        while (queueBuffer.isEmpty()) {
            System.out.println("Buffer is empty, consumer is waiting...");
            wait();
        }
        int item = queueBuffer.dequeue();
        System.out.println("Removed item from buffer: " + item);
        notifyAll();
        return item;
    }
}
