package de.concurrency.multithreading.blockingqueue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SynchronousQueueTest {

    @Test
    public void testSynchronousQueue() throws InterruptedException {
        final SynchronousQueue synchronousQueue = new SynchronousQueue();
        final Thread producerThread = createProducerThread(synchronousQueue);

        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
        newScheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Assert.assertTrue(producerThread.isAlive());
                Assert.assertTrue(synchronousQueue.isEmpty());

            }
        }, 10, 20, TimeUnit.MILLISECONDS);

        Thread.sleep(1000);
        newScheduledThreadPool.shutdownNow();

        final Thread consumerThread = createConsumerThread(synchronousQueue);

        Thread.sleep(1000);

        Assert.assertTrue(!producerThread.isAlive());
        Assert.assertTrue(!consumerThread.isAlive());
        Assert.assertTrue(synchronousQueue.isEmpty());


    }

    private Thread createProducerThread(final SynchronousQueue synchronousQueue) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    synchronousQueue.put(System.currentTimeMillis());
                    System.out.println("I produced the message");
                } catch (InterruptedException ex) {
                    throw new RuntimeException();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    private Thread createConsumerThread(final SynchronousQueue synchronousQueue) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    synchronousQueue.take();
                    System.out.println("I received the message");
                } catch (InterruptedException ex) {
                    throw new RuntimeException();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }
}
