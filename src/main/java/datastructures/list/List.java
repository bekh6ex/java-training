package ru.bekh.training.datastructures.list;

public interface List<T> {
    T get(int position);

    void append(T element);

    void insertAfter(int position, T element);

    void prepend(T element);

    void set(int position, T element);

    void remove(int position);

    int length();
}
