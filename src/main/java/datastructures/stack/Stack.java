package ru.bekh.training.datastructures.stack;

public interface Stack<T> {
    void push(T element);

    T pop();

    T top();

    boolean isEmpty();
}
