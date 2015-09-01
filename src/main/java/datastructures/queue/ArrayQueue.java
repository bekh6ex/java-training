package ru.bekh.training.datastructures.queue;

public class ArrayQueue<T> implements Queue<T> {
    public static final int STARTING_ARRAY_SIZE = 1;
    private T[] queue = createQueueArray(STARTING_ARRAY_SIZE);
    private int tailIndex = 0;
    private int headIndex = 0;

    @Override
    public void enqueue(T element) {

        if (tailIndex == queue.length ) {
            T[] newQueue = createQueueArray(queue.length * 2);
            System.arraycopy(queue, 0, newQueue, 0, queue.length);
            queue = newQueue;
        }
        queue[tailIndex] = element;
        tailIndex++;
    }

    private T[] createQueueArray(int startingArraySize) {
        return (T[]) new Object[startingArraySize];
    }

    @Override
    public T dequeue() {
        T result = queue[headIndex];
        headIndex++;
        return result;
    }

    @Override
    public T peek() {
        return queue[headIndex];
    }

    @Override
    public boolean isEmpty() {
        return tailIndex == headIndex;
    }
}
