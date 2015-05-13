package de.concurrency.multithreading.producerconsumer;

import java.util.LinkedList;
import org.testng.annotations.Test;

public class ProducentConsumerTest {

    @Test
    public void testTwoProducersAndConsumes() {

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

    @Test
    public void testFourProducersAndConsumes() {

        LinkedList<String> list = new LinkedList<String>();
        final int sizeOfBuffer = 10;

        Producer firstProducer = new Producer(list, 33, sizeOfBuffer);
        Producer secondProducer = new Producer(list, 67, sizeOfBuffer);
        Producer thirdProducer = new Producer(list, 55, sizeOfBuffer);
        Producer fourhProducer = new Producer(list, 45, sizeOfBuffer);
        Consumer firstConsumer = new Consumer(list, 88);
        Consumer secondConsumer = new Consumer(list, 12);
        Consumer thirdConsumer = new Consumer(list, 25);
        Consumer fourthConsumer = new Consumer(list, 75);

        Thread firstProducerThread = new Thread(firstProducer, "Producer I");
        Thread secondProducerThread = new Thread(secondProducer, "Producer II");
        Thread thirdProducerThread = new Thread(thirdProducer, "Producer III");
        Thread fourthProducerThread = new Thread(fourhProducer, "Producer IV");
        Thread firstConsumerThread = new Thread(firstConsumer, "Consumer I");
        Thread secondConsumerThread = new Thread(secondConsumer, "Consumer II");
        Thread thirdConsumerThread = new Thread(thirdConsumer, "Consumer III");
        Thread fourthConsumerThread = new Thread(fourthConsumer, "Consumer IV");

        firstProducerThread.start();
        firstConsumerThread.start();
        secondConsumerThread.start();
        secondProducerThread.start();
        fourthConsumerThread.start();
        thirdProducerThread.start();
        fourthProducerThread.start();
        thirdConsumerThread.start();

    }
}
