package de.concurrency.multithreading.phaser;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

import static org.testng.Assert.assertTrue;

public class PhaserTest {

    public static final int PARTIES = 20;

    @Test
    public void simplePhaserArriveTest() throws Exception {
        //given
        PhaserThread runningPhaserThread = new PhaserThread();

        //when
        new ArriveTrigger(runningPhaserThread.phaser).arriveAndWait(runningPhaserThread);

        //then
        assertTrue(runningPhaserThread.executionCompleted());

    }

    private static class PhaserThread extends Thread {
        private Phaser phaser;
        private volatile boolean completed = false;

        public PhaserThread() {
            this.phaser = new Phaser(PARTIES);
            start();
        }

        @Override
        public void run() {
            phaser.awaitAdvance(phaser.getPhase());
            completed = true;
        }

        public boolean executionCompleted() {
            return completed;
        }

    }

    private static class ArriveTrigger {

        private final ArrayList<Thread> threads;

        public ArriveTrigger(final Phaser phaser) {
            threads = new ArrayList<Thread>(PARTIES);
            for (int i = 0; i < PARTIES; i++) {
                threads.add(new ArriveThread(phaser));
            }
        }

        public void arriveAndWait(PhaserThread runningPhaserThread) throws InterruptedException {
            for (Thread thread : threads) {
                thread.start();
                thread.join();
            }
            runningPhaserThread.join(1);
        }

        private static class ArriveThread extends Thread {
            private final Phaser phaser;

            public ArriveThread(Phaser phaser) {
                this.phaser = phaser;
            }

            @Override
            public void run() {
                phaser.arrive();
            }
        }
    }
}