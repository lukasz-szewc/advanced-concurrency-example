package de.concurrency.multithreading.semaphore;

import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

public class SepahoredResource {

    private Semaphore semaphore = new Semaphore(1);
    private Semaphore secondSemaphore = new Semaphore(0);
    private final PriorityQueue<String> priorityQueue = new PriorityQueue<String>();

    public void put(String string) {
        try {
            semaphore.acquireUninterruptibly();
            priorityQueue.offer(string);
            System.out.println(Thread.currentThread().getName() + " offered message " + string + " queue size " + priorityQueue.size());
        } finally {
            secondSemaphore.release(1);
        }
    }

    public void get() {
        try {
            secondSemaphore.acquireUninterruptibly();
            String polledMessage = priorityQueue.poll();
            System.out.println(Thread.currentThread().getName() + " polled message: " 
                    + polledMessage + ", queue size  " + priorityQueue.size());
        } finally {
            semaphore.release();
        }
    }
}
