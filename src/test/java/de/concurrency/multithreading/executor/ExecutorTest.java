package de.concurrency.multithreading.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExecutorTest {

    @Test
    public void simpleExecutorTest() throws InterruptedException {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("first test something");
            }
        });

        executorService.shutdown();

        Assert.assertTrue(executorService.isShutdown());
//        Assert.assertFalse(executorService.isTerminated());

    }

    @Test
    public void simpleExecutorSubmitTest() throws InterruptedException, ExecutionException {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("print something");
            }
        }, Integer.valueOf(5));

        Integer get = future.get();
        Assert.assertEquals(get, Integer.valueOf(5));
        executorService.shutdown();

    }

    @Test
    public void testSchedule() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<Long> schedule = executorService.schedule(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return System.currentTimeMillis();
            }
        }, 1, TimeUnit.SECONDS);
        System.out.println(schedule.getDelay(TimeUnit.SECONDS));
        Long longer = schedule.get(5, TimeUnit.SECONDS);
        Assert.assertNotNull(longer);
    }

    @Test
    public void testScheduleWithDelay() throws Exception {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition newCondition = reentrantLock.newCondition();
        SomeRunnable someRunnable = new SomeRunnable(reentrantLock, newCondition);

        executorService.scheduleWithFixedDelay(someRunnable, 10, 5, TimeUnit.MILLISECONDS);

        reentrantLock.lock();
        try {
            newCondition.await();
            executorService.shutdownNow();
        } finally {
            reentrantLock.unlock();
        }

    }

    @Test
    public void testScheduleWithDelayCountDownLatch() throws Exception {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        final CountDownLatch countDownLatch = new CountDownLatch(15);

        executorService.scheduleWithFixedDelay(new Runnable() {
            private int counter;

            @Override
            public void run() {
                countDownLatch.countDown();
                counter++;
                System.out.println("Yatzee" + counter);
            }
        }, 10, 5, TimeUnit.MILLISECONDS);

        countDownLatch.await();
        executorService.shutdownNow();

    }

    public static class SomeRunnable implements Runnable {

        private int value = 0;
        private final ReentrantLock reentrantLock;
        private final Condition newCondition;

        private SomeRunnable(ReentrantLock reentrantLock, Condition newCondition) {
            this.reentrantLock = reentrantLock;
            this.newCondition = newCondition;
        }

        @Override
        public void run() {
            reentrantLock.lock();
            try {
                if (value == 15) {
                    newCondition.signal();
                    try {
                        newCondition.await();
                    } catch (InterruptedException ex) {
                        System.out.println("expected exception");
                        return ;
                    }
                }
                value++;
                System.out.println(value);
            } finally {
                reentrantLock.unlock();
            }
        }
    }
}
