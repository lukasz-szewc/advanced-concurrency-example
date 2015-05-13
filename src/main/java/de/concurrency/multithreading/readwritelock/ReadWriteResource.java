package de.concurrency.multithreading.readwritelock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteResource {

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
    private ArrayList<String> arrayList = new ArrayList<String>();

    public void put(String putString) {
        writeLock.lock();
        try {
            arrayList.add(putString);
            System.out.println(Thread.currentThread().getName());
        } finally {
            writeLock.unlock();
        }
    }

    public void iterate() {
        readLock.lock();
        try {
            for (String string : arrayList) {
                System.out.println(Thread.currentThread().getName() + " " + string);
            }
        } finally {
            readLock.unlock();
        }
    }

}
