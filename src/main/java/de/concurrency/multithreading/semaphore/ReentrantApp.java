package de.concurrency.multithreading.semaphore;

public class ReentrantApp {

    public static void main(String[] args) {
        SepahoredResource lockedResource = new SepahoredResource();

        LockConsumer lockConsumer1 = new LockConsumer(lockedResource, 80);
        LockConsumer lockConsumer2 = new LockConsumer(lockedResource, 80);

        LockProducer lockProducer1 = new LockProducer(lockedResource, 100);
        LockProducer lockProducer2 = new LockProducer(lockedResource, 60);

        Thread thread1 = new Thread(lockConsumer1, "CONSUMER I");
        Thread thread2 = new Thread(lockConsumer2, "CONSUMER II");
        Thread thread3 = new Thread(lockProducer1, "PRODUCER I");
        Thread thread4 = new Thread(lockProducer2, "PRODUCER II");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

}
