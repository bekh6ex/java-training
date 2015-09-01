package ru.bekh.training.datastructures.queue;

public interface Queue<T> {
    void enqueue(T element);
    T dequeue();
    T peek();
    boolean isEmpty();
}
