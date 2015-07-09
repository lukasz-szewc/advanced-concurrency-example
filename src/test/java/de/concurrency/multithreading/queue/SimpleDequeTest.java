package de.concurrency.multithreading.queue;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Deque;
import java.util.LinkedList;

public class SimpleDequeTest {

    public static final String VALUE = "value";

    @Test
    public void sameElementIsPeekedFromDequeWhenOneElementIsOffered() throws Exception {
        //given
        Deque<String> queue = newDeque();

        //when
        queue.offerLast(VALUE);

        //then
        Assert.assertEquals(queue.peekFirst(), VALUE);
        Assert.assertEquals(queue.peekLast(), VALUE);
        Assert.assertEquals(queue.peek(), VALUE);
    }

    private LinkedList<String> newDeque() {
        return new LinkedList<String>();
    }
}
