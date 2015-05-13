package de.concurrency.multithreading.latch;

import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;

public class LatchedTest {

    @Test
    public void testLatchedCollection(){
        
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        
        final PriorityQueue<String> priorityQueue = new PriorityQueue<String>();
        
        final LatchResourceReader latchResourceReader = new LatchResourceReader(priorityQueue, countDownLatch);
        
        for (int i = 0; i < 10; i++) {
            final Thread consumerThread = new Thread(latchResourceReader, "Consumer Thread " + i);
            consumerThread.setDaemon(false);
            consumerThread.start();
        }
        
        for (int counter = 0; counter < 5; counter++) {
            Thread thread = createPriducerThreadInstance(counter, priorityQueue, countDownLatch);
            thread.start();
            waitForAwhile();
            
        }
    }

    private void waitForAwhile() {
        try {
            Thread.sleep(1033);
        } catch (InterruptedException ex) {
            Logger.getLogger(LatchedTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Thread createPriducerThreadInstance(int i, final PriorityQueue<String> priorityQueue, final CountDownLatch countDownLatch) {
        Thread thread = new Thread("Producer " + i) {

            @Override
            public void run() {
                System.out.printf("I am thread: %s and I am producing message..." , Thread.currentThread().getName());
                System.out.println();
                priorityQueue.add(Thread.currentThread().getName());
                countDownLatch.countDown();
            }
            
        };
        return thread;
    }
    
}
