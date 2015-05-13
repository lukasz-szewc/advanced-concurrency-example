package de.concurrency.multithreading.producerconsumer;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class Producer implements Runnable {

    private final LinkedList<String> linkedList;
    private int numberOfProducedMessages;
    private final int maxSize;

    Producer(LinkedList<String> list, int i) {
        this(list, i , 1);
    }

    Producer(LinkedList<String> list, int i, int maxSize) {
        this.maxSize = maxSize;
        this.linkedList = list;
        this.numberOfProducedMessages = i;
    }

    @Override
    public void run() {
        while (numberOfProducedMessages > 0) {
            synchronized (linkedList) {


                while (linkedList.size() == maxSize) {
                    try {
                        linkedList.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                final String name = Thread.currentThread().getName();

                linkedList.add(name);

                System.out.println("[" + Thread.currentThread().getName() + "]"
                        + " I have produced message: " + name
                        + " size of queue: " + linkedList.size());

                numberOfProducedMessages--;
                linkedList.notifyAll();

            }
        }
    }
}
