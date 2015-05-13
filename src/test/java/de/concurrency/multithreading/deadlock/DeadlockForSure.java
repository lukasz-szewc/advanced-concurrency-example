package de.concurrency.multithreading.deadlock;

import java.util.LinkedList;
import org.testng.annotations.Test;

/**
 *
 */
public class DeadlockForSure {

    @Test(enabled = false)
    public void method() throws InterruptedException {
        LinkedList<String> firstLinkedList = new LinkedList<String>();
        LinkedList<String> secondLinkedList = new LinkedList<String>();
        final Thread first = new Thread(new DeadlockRunnable(firstLinkedList, secondLinkedList));
        first.setDaemon(false);
        first.start();
        final Thread secodn = new Thread(new DeadlockMirrorRunnable(firstLinkedList, secondLinkedList));
        secodn.setDaemon(false);
        secodn.start();

        Thread.sleep(222222);

    }

    public static class DeadlockRunnable implements Runnable {

        private final LinkedList<String> firstLinkedList;
        private final LinkedList<String> secondLinkedList;

        private DeadlockRunnable(LinkedList<String> firstLinkedList, LinkedList<String> secondLinkedList) {
            this.firstLinkedList = firstLinkedList;
            this.secondLinkedList = secondLinkedList;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (firstLinkedList) {
                    System.out.println(Thread.currentThread().getName() + "outer");
                    synchronized (secondLinkedList) {
                        System.out.println(Thread.currentThread().getName() + "inner");
                    }
                }
            }
        }
    }

    public static class DeadlockMirrorRunnable implements Runnable {

        private final LinkedList<String> firstLinkedList;
        private final LinkedList<String> secondLinkedList;

        private DeadlockMirrorRunnable(LinkedList<String> firstLinkedList, LinkedList<String> secondLinkedList) {
            this.firstLinkedList = firstLinkedList;
            this.secondLinkedList = secondLinkedList;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (secondLinkedList) {
                    System.out.println(Thread.currentThread().getName() + "outer");
                    synchronized (firstLinkedList) {
                        System.out.println(Thread.currentThread().getName() + "inner");
                    }
                }
            }
        }
    }
}
