package de.concurrency.multithreading.semaphore;

public class LockConsumer implements Runnable {
    private final SepahoredResource lockedResource;
    private final int maxSize;

    public LockConsumer(SepahoredResource lockedResource, int maxSize) {
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
