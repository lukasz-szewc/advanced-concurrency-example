package de.concurrency.multithreading.blockingqueue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinkedBlockingQueueTest {

    @Test
    public void testDeque() throws InterruptedException {
        final int size = 190000;
        LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();
        Runnable producerRunnable = createProducerRunnable(size, linkedBlockingDeque);
        Runnable consumerRunnable = constructConsumerRunnable(size, linkedBlockingDeque);

        Thread producer1 = new Thread(producerRunnable);
        Thread producer2 = new Thread(producerRunnable);

        Thread cosnumer1 = new Thread(consumerRunnable);
        Thread cosnumer2 = new Thread(consumerRunnable);

        cosnumer1.start();
        producer1.start();
        cosnumer2.start();
        producer2.start();

        cosnumer1.join();
        cosnumer2.join();

        Assert.assertEquals(linkedBlockingDeque.size(), 0);
    }

    private Runnable createProducerRunnable(final int size, final BlockingDeque arrayBlockingQueue) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    try {
                        if (i % 2 == 0) {
                            arrayBlockingQueue.putLast(String.valueOf(i));
                        } else {
                            arrayBlockingQueue.putFirst(String.valueOf(i));
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LinkedBlockingQueueTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        return runnable;
    }

    private Runnable constructConsumerRunnable(final int size, final BlockingDeque arrayBlockingQueue) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    try {
                        if (i % 2 == 0) {
                            arrayBlockingQueue.takeFirst();
                        } else {
                            arrayBlockingQueue.takeLast();
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LinkedBlockingQueueTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }
}
