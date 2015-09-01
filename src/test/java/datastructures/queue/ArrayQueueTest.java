package ru.bekh.training.datastructures.queue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArrayQueueTest {

    @Test
    public void isEmpty_JustCreated_ReturnsTrue() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();

        assertThat(queue.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void isEmpty_ElementEnqueued_ReturnsFalse() {
        ArrayQueue<Integer> queue = new ArrayQueue<>();

        queue.enqueue(1);

        assertThat(queue.isEmpty(), is(equalTo(false)));
    }

}