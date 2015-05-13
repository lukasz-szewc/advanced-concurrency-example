package de.concurrency.multithreading.latch;

import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;

class LatchResourceReader implements Runnable {

    private final PriorityQueue<String> priorityQueue;
    private final CountDownLatch countDownLatch;

    LatchResourceReader(PriorityQueue<String> priorityQueue, CountDownLatch countDownLatch) {
        this.priorityQueue = priorityQueue;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started....");
        try {
            countDownLatch.await();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println(Thread.currentThread().getName() + " size of queue: " + priorityQueue.size());

    }
}
