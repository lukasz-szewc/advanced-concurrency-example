package de.concurrency.multithreading.reentractlocks;

public class LockProducer implements Runnable {
    private final LockedResource lockedResource;
    private final int maxSize;

    public LockProducer(LockedResource lockedResource, int maxSize) {
        this.lockedResource = lockedResource;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        int size = maxSize;
        for (int i = 0; i < size; i++) {
            lockedResource.put(Thread.currentThread().getName() + " " + i);
        }
    }

}
