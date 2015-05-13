package de.concurrency.multithreading.producerconsumer;

import java.util.LinkedList;

public class ProducerConsumerExecution {

    public static void main(String[] args) {

        LinkedList<String> list = new LinkedList<String>();

        Producer firstProducer = new Producer(list, 33);
        Producer secondProducer = new Producer(list, 67);
        Consumer firstConsumer = new Consumer(list, 88);
        Consumer secondConsumer = new Consumer(list, 12);

        Thread firstProducerThread = new Thread(firstProducer, "Producer I");
        Thread secondProducerThread = new Thread(secondProducer, "Producer II");
        Thread firstConsumerThread = new Thread(firstConsumer, "Consumer I");
        Thread secondConsumerThread = new Thread(secondConsumer, "Consumer II");

        firstProducerThread.start();
        firstConsumerThread.start();
        secondConsumerThread.start();
        secondProducerThread.start();
    }

}
