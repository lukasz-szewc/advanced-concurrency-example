package de.concurrency.multithreading.reentractlocks;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LockedResource {

    public static final int SIZE_OF_BUFFER = 3;
    private final ReentrantLock reentrantLock;
    private final PriorityQueue<String> priorityQueue;
    private final Condition waitForLessThanFullCondition;
    private final Condition fullCondition;

    public LockedResource() {
        priorityQueue = new PriorityQueue<String>();
        reentrantLock = new ReentrantLock();
        waitForLessThanFullCondition = reentrantLock.newCondition();
        fullCondition = reentrantLock.newCondition();
    }

    public void put(String string) {
        reentrantLock.lock();
        try {
            while (priorityQueue.size() == SIZE_OF_BUFFER) {
                System.out.println("[LOCKED RESOURCE] SIZE OF BUFFER = " + priorityQueue.size()
                        + ", THREAD " + Thread.currentThread().getName() + " IS WAITING");
                waitForLessThanFullCondition.await();
            }
            priorityQueue.offer(string);
            System.out.println("[LOCKED RESOURCE] NEW MESSAGE JUST ADDED BY: " + Thread.currentThread().getName() + " BUFFER SIZE: " + priorityQueue.size());
            fullCondition.signal();
        } catch (InterruptedException ex) {
            Logger.getLogger(LockedResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void get() {
        reentrantLock.lock();
        try {
            while (priorityQueue.size() == 0) {
                try {
                    System.out.println("[LOCKED RESOURCE] SIZE OF BUFFER = " + priorityQueue.size()
                            + ", THREAD " + Thread.currentThread().getName() + " IS WAITING");
                    fullCondition.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(LockedResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            priorityQueue.poll();
            System.out.println("[LOCKED RESOURCE] MESSAGE CONSUMERD BY: " + Thread.currentThread().getName() + " BUFFER SIZE: " + priorityQueue.size());
            waitForLessThanFullCondition.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    public int getSize() {
        reentrantLock.lock();
        try {
            return priorityQueue.size();
        } finally {
            reentrantLock.unlock();
        }

    }
}
