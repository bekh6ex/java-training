package ru.bekh.training.datastructures.queue;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArrayQueueTest extends QueueTest {

    @Override
    protected Queue<Integer> createQueue() {
        return new ArrayQueue<>();
    }

    @Test
    public void elementsEnqueuedAndDequeued_SizeOfInternalArrayDoesNotChange() throws Exception {
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
            queue.dequeue();
        }

        assertLengthOfArrayIs(2);
    }

    private void assertLengthOfArrayIs(int lengthOfArray) throws NoSuchFieldException, IllegalAccessException {
        Field queueField = ArrayQueue.class.getDeclaredField("queue");
        queueField.setAccessible(true);
        Object[] queueArray = (Object[]) queueField.get(queue);

        assertThat(queueArray.length, is(equalTo(lengthOfArray)));
    }
}