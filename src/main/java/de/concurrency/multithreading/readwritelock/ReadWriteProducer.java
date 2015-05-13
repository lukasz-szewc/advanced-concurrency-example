package de.concurrency.multithreading.readwritelock;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ReadWriteProducer implements Runnable {
    private final ReadWriteResource lockedResource;
    private final int maxSize;

    public ReadWriteProducer(ReadWriteResource lockedResource, int maxSize) {
        this.lockedResource = lockedResource;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        int size = maxSize;
        for (int i = 0; i < size; i++) {
            lockedResource.put(String.valueOf(i));
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReadWriteProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
