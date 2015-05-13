package de.concurrency.multithreading.interruption;

public class InterruptionApp {

    public static void main(String[] args) {

        Object lock = new Object();

        CountingThread countingThread = new CountingThread(lock);
        countingThread.start();

        while (true) {
            synchronized (lock) {
                if (countingThread.getI() >= 7) {
                    countingThread.interrupt();
                    break;
                }
            }
        }


    }
}

class CountingThread extends Thread {

    private volatile int i = 0;
    private final Object lock;

    CountingThread(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println("interrupted " + ex.getLocalizedMessage());
                    break;
                }
                System.out.println("increment i: " + ++i);
                lock.notify();
            }
        }

    }

    public int getI() {
        return i;
    }
}