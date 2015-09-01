package ru.bekh.training.datastructures.queue;

public class ArrayQueueTest extends QueueTest {

    @Override
    protected Queue<Integer> createQueue() {
        return new ArrayQueue<>();
    }
}