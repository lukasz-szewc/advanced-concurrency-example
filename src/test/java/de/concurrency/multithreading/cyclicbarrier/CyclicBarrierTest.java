package de.concurrency.multithreading.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;

public class CyclicBarrierTest {

    @Test
    public void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, constructRunnable());
        {
            Thread thread = new Thread(new BarrierBraker(cyclicBarrier));
            thread.start();
        }
        {
            Thread thread = new Thread(new BarrierBraker(cyclicBarrier));
            thread.start();
        }
        {
            Thread thread = new Thread(new BarrierBraker(cyclicBarrier));
            thread.start();
        }
    }

    private Runnable constructRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Executed!");
            }
        };
    }

    private static class BarrierBraker implements Runnable {

        private final CyclicBarrier cyclicBarrier;

        public BarrierBraker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                System.out.println("I am FREE: " + Thread.currentThread().getName());
            } catch (InterruptedException ex) {
                Logger.getLogger(CyclicBarrierTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                System.out.println("Barrier broken!");
            }
        }
    }
}
