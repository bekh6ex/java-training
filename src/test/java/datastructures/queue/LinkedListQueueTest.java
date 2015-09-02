package ru.bekh.training.datastructures.queue;

public class LinkedListQueueTest extends QueueTest {

    @Override
    protected Queue<Integer> createQueue() {
        return new LinkedListQueue<>();
    }
}