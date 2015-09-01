package ru.bekh.training.datastructures.queue;

public class ArrayQueue<T> implements Queue<T> {
    public static final int STARTING_ARRAY_SIZE = 1;
    private T[] queue = (T[]) new Object[STARTING_ARRAY_SIZE];
    private int headIndex = 0;

    @Override
    public void enqueue(T element) {
        headIndex++;
    }

    @Override
    public T dequeue() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return headIndex == 0;
    }
}
