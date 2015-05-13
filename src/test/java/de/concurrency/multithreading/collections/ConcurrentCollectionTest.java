package de.concurrency.multithreading.collections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConcurrentCollectionTest {

    @Test
    public void testConcurrentHashSet() throws InterruptedException {

        final AtomicInteger atomicInteger = new AtomicInteger();
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    Object putIfAbsent = concurrentHashMap.putIfAbsent(
                            Integer.valueOf(i), System.currentTimeMillis());
                    if (putIfAbsent != null) {
                        atomicInteger.incrementAndGet();
                    }
                }

            }
        };

        for (int i = 0; i < 101; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }

        Thread.sleep(3000);
        Assert.assertEquals(concurrentHashMap.size(), 1000);
        Assert.assertEquals(atomicInteger.get(), 100000);

    }
}
