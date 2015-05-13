package de.concurrency.multithreading.reentractlocks;

public class LockConsumer implements Runnable {
    private final LockedResource lockedResource;
    private final int maxSize;

    public LockConsumer(LockedResource lockedResource, int maxSize) {
        this.lockedResource = lockedResource;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        int size = maxSize;
        for (int i = 0; i < size; i++) {
            lockedResource.get();
        }
    }

}
