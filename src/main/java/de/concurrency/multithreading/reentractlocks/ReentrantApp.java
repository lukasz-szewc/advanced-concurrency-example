/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.concurrency.multithreading.reentractlocks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantApp {

    public static void main(String[] args) {
        Component component = new Component();
        for (int i = 0; i < 10; i++) {

            new Thread(component).start();
        }
    }

    private static class Component implements Runnable {
        private final ReentrantLock reentrantLock;

        public Component() {
            reentrantLock = new ReentrantLock();

        }

        @Override
        public void run() {
            reentrantLock.lock();
            try {
                firstMethod();
            } finally {
                reentrantLock.unlock();
            }
        }

        private void firstMethod() {
            reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "first method , Number of held locks : " + reentrantLock.getHoldCount());
                secondMethod();
            } finally {
                reentrantLock.unlock();
            }
        }
        private void secondMethod() {
            reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "second method , Number of held locks : " + reentrantLock.getHoldCount());
                thirdMethod();
            } finally {
                reentrantLock.unlock();
            }
        }

        private void thirdMethod() {
            reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "third method , Number of held locks : " + reentrantLock.getHoldCount());
            } finally {
                reentrantLock.unlock();
            }
        }

    }

}
