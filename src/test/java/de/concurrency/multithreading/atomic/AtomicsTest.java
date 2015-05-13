package de.concurrency.multithreading.atomic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 */
public class AtomicsTest {

    @Test(enabled = true)
    public void methodTest() throws InterruptedException {
        final int threadCount = 10000;
        final AtomicInteger atomicInteger = new AtomicInteger();
        final ArrayList<Boolean> list = new ArrayList<Boolean>();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    synchronized (atomicInteger) {
                        int value = atomicInteger.get();
                        boolean compareAndSet = atomicInteger.compareAndSet(value, value + 1);
                        synchronized (list) {
                            if (compareAndSet) {
                                list.add(compareAndSet);

                            }
                        }
                    }
                }
            };
            thread.start();
        }

        Thread.sleep(1000);

        Assert.assertEquals(list.size(), threadCount);

    }

    @Test(enabled = true)
    public void method() throws InterruptedException {
        final int threadCount = 10000;
        final HashSet<Integer> setWithNumers = new HashSet<Integer>();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        final AtomicInteger atomicInteger = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    int incrementAndGet = atomicInteger.getAndIncrement();
                    synchronized (setWithNumers) {
                        setWithNumers.add(incrementAndGet);
                    }
                    countDownLatch.countDown();
                }
            };
            thread.start();
        }


        countDownLatch.await();
        Assert.assertEquals(setWithNumers.size(), threadCount);

        for (int counter = 0; counter < threadCount; counter++) {
            Assert.assertTrue(setWithNumers.contains(counter));
        }

    }
}
