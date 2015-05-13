package de.concurrency.multithreading.producerconsumer;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private final LinkedList<String> linkedList;
    private int consumedNumber;

    Consumer(LinkedList<String> list, int numberOfConsumedMessages) {
        linkedList = list;
        consumedNumber = numberOfConsumedMessages;
    }

    @Override
    public void run() {
        while (consumedNumber > 0) {
            synchronized (linkedList) {
                while (linkedList.isEmpty()) {
                    try {
                        linkedList.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                String string = linkedList.removeFirst();
                System.out.printf("[%s] I have consumed message with id: "
                        + "%s - size of queue: %d \n",
                        Thread.currentThread().getName(), string, linkedList.size());
                consumedNumber--;
                linkedList.notifyAll();
            }
        }
    }
}
