package de.concurrency.multithreading.blockingqueue;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransferQueueTest {

    private final AtomicLong atomicLong = new AtomicLong();

    @Test
    public void testTransferQueue() throws InterruptedException {

        final LinkedTransferQueue linkedTransferQueue = new LinkedTransferQueue();
        startOneProducerThread(linkedTransferQueue);

        Assert.assertEquals(linkedTransferQueue.size(), 1);
        Assert.assertEquals(linkedTransferQueue.getWaitingConsumerCount(), 0);

        startOneConsumerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 0);
        Assert.assertEquals(linkedTransferQueue.getWaitingConsumerCount(), 0);

        startOneConsumerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 0);
        Assert.assertEquals(linkedTransferQueue.getWaitingConsumerCount(), 1);

        startOneConsumerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 0);
        Assert.assertEquals(linkedTransferQueue.getWaitingConsumerCount(), 2);

        startOneProducerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 0);
        Assert.assertEquals(linkedTransferQueue.getWaitingConsumerCount(), 1);

        startOneProducerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 0);
        Assert.assertEquals(linkedTransferQueue.getWaitingConsumerCount(), 0);

        startOneProducerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 1);

        startOneProducerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 2);

        startOneConsumerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 1);
        Assert.assertEquals(linkedTransferQueue.getWaitingConsumerCount(), 0);

        startOneConsumerThread(linkedTransferQueue);
        Assert.assertEquals(linkedTransferQueue.size(), 0);
        Assert.assertEquals(linkedTransferQueue.getWaitingConsumerCount(), 0);
    }

    private Thread startOneProducerThread(final LinkedTransferQueue linkedTransferQueue) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    linkedTransferQueue.transfer(atomicLong.incrementAndGet());
                } catch (InterruptedException ex) {
                    Logger.getLogger(TransferQueueTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        final Thread thread = new Thread(runnable);
        thread.start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            Logger.getLogger(TransferQueueTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return thread;
    }

    private void startOneConsumerThread(final LinkedTransferQueue linkedTransferQueue) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    linkedTransferQueue.take();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TransferQueueTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        final Thread thread = new Thread(runnable);
        thread.start();

        try {
            Thread.sleep(220);
        } catch (InterruptedException ex) {
            Logger.getLogger(TransferQueueTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
