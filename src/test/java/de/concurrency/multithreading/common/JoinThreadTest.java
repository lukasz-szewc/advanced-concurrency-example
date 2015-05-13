package de.concurrency.multithreading.common;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;

/**
 *
 */
public class JoinThreadTest {

    @Test
    public void method() throws InterruptedException {
        JoinedThread joinedThread1 = new JoinedThread(null);
        JoinedThread joinedThread2 = new JoinedThread(joinedThread1);
        JoinedThread joinedThread3 = new JoinedThread(joinedThread2);
        
        joinedThread1.setName("First");
        joinedThread2.setName("Second");
        joinedThread3.setName("Third");

        joinedThread3.start();
        joinedThread2.start();
        joinedThread1.start();
    }

    public static class JoinedThread extends Thread {
        private final Thread thread;

        public JoinedThread(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            if (thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(JoinThreadTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Start of thread: " + Thread.currentThread().getName());
            System.out.println("Stop  of thread: " + Thread.currentThread().getName());
        }

    }

}
