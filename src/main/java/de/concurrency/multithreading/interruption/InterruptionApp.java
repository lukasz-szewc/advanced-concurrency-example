package de.concurrency.multithreading.interruption;

public class InterruptionApp {

    public static void main(String[] args) {
        CountingThread countingThread = new CountingThread();
        countingThread.start();

        while (true) {
            if (countingThread.getI() >= 7) {
                countingThread.interrupt();
                break;
            }
        }
    }

    static class CountingThread extends Thread {

        private volatile int i = 0;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println("interrupted " + ex.getLocalizedMessage());
                    break;
                }
                System.out.println("increment i: " + ++i);
            }

        }

        public int getI() {
            return i;
        }

    }
}