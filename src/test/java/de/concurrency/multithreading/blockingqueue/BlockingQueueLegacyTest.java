package de.concurrency.multithreading.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BlockingQueueLegacyTest {

    @Test
    public void testSmting2() throws InterruptedException {
        final int size = 190000;
        final ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(20);
        Runnable producerRunnable = createProducerRunnable(size, arrayBlockingQueue);
        Runnable consumerRunnable = constructConsumerRunnable(size, arrayBlockingQueue);

        Thread producer1 = new Thread(producerRunnable);
        Thread producer2 = new Thread(producerRunnable);

        Thread consumer1 = new Thread(consumerRunnable);
        Thread consumer2 = new Thread(consumerRunnable);

        consumer1.start();
        producer1.start();
        consumer2.start();
        producer2.start();

        consumer1.join();
        consumer2.join();

        Assert.assertEquals(arrayBlockingQueue.size(), 0);
    }

    @Test
    public void testSmting() throws InterruptedException {
        final ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(2);
        Runnable producer = constructProducer(arrayBlockingQueue);
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        newFixedThreadPool.execute(producer);
        newFixedThreadPool.execute(producer);
        newFixedThreadPool.execute(producer);

        Thread.sleep(400);
        Assert.assertEquals(arrayBlockingQueue.size(), 2);
    }

    @Test
    public void testProdConsumer() throws InterruptedException {
        final ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);
        Runnable producer = constructProducer(arrayBlockingQueue);
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        newFixedThreadPool.execute(producer);
        newFixedThreadPool.execute(producer);
        newFixedThreadPool.execute(producer);

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return arrayBlockingQueue.take();
            }
        };

        ExecutorService newFixedThreadPool2 = Executors.newFixedThreadPool(3);
        newFixedThreadPool2.submit(callable);
        newFixedThreadPool2.submit(callable);

        Thread.sleep(400);

        Assert.assertEquals(arrayBlockingQueue.size(), 1);
    }

    private Runnable constructProducer(final ArrayBlockingQueue arrayBlockingQueue) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    arrayBlockingQueue.put(System.currentTimeMillis());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    private Runnable createProducerRunnable(final int size, final ArrayBlockingQueue arrayBlockingQueue) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    try {
                        arrayBlockingQueue.put(String.valueOf(i));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BlockingQueueLegacyTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }

    private Runnable constructConsumerRunnable(final int size, final ArrayBlockingQueue arrayBlockingQueue) {
        return new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    try {
                        arrayBlockingQueue.take();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BlockingQueueLegacyTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }
}
