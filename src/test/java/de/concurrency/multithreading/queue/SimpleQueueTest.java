package de.concurrency.multithreading.queue;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class SimpleQueueTest {

    @Test
    public void shouldThrowNoSuchElementExceptionWhenRemoveIsInvokedOnEmptyQueue() throws Exception {
        //given
        Queue<String> queue = newQueue();

        //when
        NoSuchElementException e = elementIsRemovedFromEmptyQueue(queue);

        //then
        noSuchElementExceptionIsThrown(e);
    }


    @Test
    public void shouldThrowNoSuchElementExceptionElementMethodIsInvokedOnEmptyQueue() throws Exception {
        //given
        Queue<String> queue = newQueue();

        //when
        NoSuchElementException e = elementMethodIsInvokedOnEmptyQueue(queue);

        //then
        noSuchElementExceptionIsThrown(e);
    }

    @Test
    public void shouldReturnNullWhenElementIsPolledFromEmptyQueue() throws Exception {
        //given
        Queue<String> queue = newQueue();

        //when
        String element = elementIsPolledFromEmptyQueue(queue);

        //then
        Assert.assertNull(element);
    }

    @Test
    public void shouldReturnNullWhenElementIsPeekedFromEmptyQueue() throws Exception {
        //given
        Queue<String> queue = newQueue();

        //when
        String element = elementIsPeekedFromEmptyQueue(queue);

        //then
        Assert.assertNull(element);
    }

    private String elementIsPeekedFromEmptyQueue(Queue<String> queue) {
        return queue.peek();
    }

    private String elementIsPolledFromEmptyQueue(Queue<String> queue) {
        return queue.poll();
    }

    private void noSuchElementExceptionIsThrown(NoSuchElementException e) {
        Assert.assertNotNull(e);
    }

    private NoSuchElementException elementMethodIsInvokedOnEmptyQueue(Queue<String> queue) {
        try {
            queue.element();
            return null;
        } catch (NoSuchElementException e) {
            return e;
        }
    }

    private NoSuchElementException elementIsRemovedFromEmptyQueue(Queue<String> queue) {
        try {
            queue.remove();
            return null;
        } catch (NoSuchElementException e) {
            return e;
        }
    }

    private LinkedList<String> newQueue() {
        return new LinkedList<String>();
    }
}
