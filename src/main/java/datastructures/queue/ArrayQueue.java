package ru.bekh.training.datastructures.queue;

public class ArrayQueue<T> implements Queue<T> {
    private static final int STARTING_ARRAY_SIZE = 2;
    private T[] queue = createQueueArray(STARTING_ARRAY_SIZE);
    private int tailIndex = 0;
    private int headIndex = 0;

    @Override
    public void enqueue(T element) {
        if (getUsedSize() == queue.length - 1) {
            T[] newQueue = createQueueArray(queue.length * 2);
            if (tailIndex > headIndex) {
                System.arraycopy(queue, headIndex, newQueue, 0, getUsedSize());
                tailIndex = getUsedSize();
                headIndex = 0;
            } else {
                System.arraycopy(queue, headIndex, newQueue, 0, queue.length - headIndex);
                System.arraycopy(queue, 0, newQueue, queue.length - headIndex, tailIndex);
                tailIndex = getUsedSize();
                headIndex = 0;
            }

            queue = newQueue;
        }
        queue[tailIndex] = element;
        tailIndex++;
        if (tailIndex == queue.length) {
            tailIndex = 0;
        }
    }

    @Override
    public T dequeue() {
        T result = queue[headIndex];
        queue[headIndex] = null;
        headIndex++;
        if (headIndex == queue.length) {
            headIndex = 0;
        }
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

    private T[] createQueueArray(int size) {
        return (T[]) new Object[size];
    }

    private int getUsedSize() {
        int result = tailIndex - headIndex;
        if (result < 0) {
            result += queue.length;
        }
        return result;
    }
}
