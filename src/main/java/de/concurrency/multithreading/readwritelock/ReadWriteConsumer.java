package de.concurrency.multithreading.readwritelock;

public class ReadWriteConsumer implements Runnable {

    private final ReadWriteResource lockedResource;

    public ReadWriteConsumer(ReadWriteResource lockedResource) {
        this.lockedResource = lockedResource;
    }

    @Override
    public void run() {
        while (true) {
            lockedResource.iterate();
        }
    }
}
