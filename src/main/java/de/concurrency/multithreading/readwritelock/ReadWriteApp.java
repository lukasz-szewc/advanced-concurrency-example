package de.concurrency.multithreading.readwritelock;

public class ReadWriteApp {

    public static void main(String[] args) {
        final ReadWriteResource readWriteResource = new ReadWriteResource();
        ReadWriteConsumer readWriteConsumer = new ReadWriteConsumer(readWriteResource);
        ReadWriteProducer readWriteProducer = new ReadWriteProducer(readWriteResource, 30);
       
        Thread producerThread = new Thread(readWriteProducer , "producer thread");
        Thread firstConsumerThread = new Thread(readWriteConsumer, "consumer I thread");
        Thread secondConsumerThread = new Thread(readWriteConsumer, "consumer II thread");
        
        producerThread.start();
        firstConsumerThread.start();
        secondConsumerThread.start();
        
    }
}
