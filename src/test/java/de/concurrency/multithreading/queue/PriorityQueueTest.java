package de.concurrency.multithreading.queue;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {

    @Test
    public void priorityQueueWillOfferElementsAccordingToNaturalOrder() throws Exception {
        //given
        PriorityQueue<String> priorityQueue = new PriorityQueue<String>();

        //when
        priorityQueue.offer("z");
        priorityQueue.offer("a");

        //when
        Assert.assertEquals(priorityQueue.peek(), "a");
    }

    @Test
    public void priorityQueueWillAddElementsAccordingToNaturalOrder() throws Exception {
        //given
        PriorityQueue<String> priorityQueue = new PriorityQueue<String>();

        //when
        priorityQueue.add("z");
        priorityQueue.add("a");

        //when
        Assert.assertEquals(priorityQueue.peek(), "a");
    }

    @Test
    public void priorityQueueWillOrderElementsAccordingToComparator() throws Exception {
        //given
        PriorityQueue<String> priorityQueue = new PriorityQueue<String>(reverseComparator());

        //when
        priorityQueue.add("a");
        priorityQueue.add("k");

        //when
        Assert.assertEquals(priorityQueue.peek(), "k");
    }

    private Comparator<String> reverseComparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (-1) * o1.compareTo(o2);
            }
        };
    }

}
