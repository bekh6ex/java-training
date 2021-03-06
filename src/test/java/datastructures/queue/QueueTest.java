package ru.bekh.training.datastructures.queue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

abstract public class QueueTest {
    protected Queue<Integer> queue = createQueue();

    abstract protected Queue<Integer> createQueue();

    @Test
    public void isEmpty_JustCreated_ReturnsTrue() {
        assertThat(queue.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void isEmpty_ElementEnqueued_ReturnsFalse() {
        queue.enqueue(1);

        assertThat(queue.isEmpty(), is(equalTo(false)));
    }

    @Test
    public void isEmpty_ElementAndDequeued_ReturnsTrue() {
        queue.enqueue(1);
        queue.dequeue();

        assertThat(queue.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void dequeue_ElementEnqueued_ReturnsThatElement() {
        queue.enqueue(1);

        assertThat(queue.dequeue(), is(equalTo(1)));
    }

    @Test
    public void dequeue_MultipleElementsEnqueued_ReturnsThatElementsInSameOrder()
    {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertThat(queue.dequeue(), is(equalTo(1)));
        assertThat(queue.dequeue(), is(equalTo(2)));
        assertThat(queue.dequeue(), is(equalTo(3)));
    }

    @Test
    public void peek_MultipleElementsEnqueued_ReturnsFirstElement()
    {
        queue.enqueue(1);
        queue.enqueue(2);

        assertThat(queue.peek(), is(equalTo(1)));
    }

    @Test
    public void peek_CalledSeveralTimes_ReturnsSameElement()
    {
        queue.enqueue(1);
        queue.enqueue(2);

        assertThat(queue.peek(), is(equalTo(1)));
        assertThat(queue.peek(), is(equalTo(1)));
    }

    @Test
    public void multipleElementsEnqueued_ShouldBeDequeuedInCorrectOrder() throws Exception {
        for (int i = 1; i < 1000; i++) {
            for (int j = 1; j <= i; j++) {
                queue.enqueue(j);
            }

            for (int j = 1; j <= i; j++) {
                assertThat(queue.isEmpty(), is(false));
                assertThat(queue.dequeue(), is(equalTo(j)));
            }
            assertThat(queue.isEmpty(), is(true));
        }
    }
}
