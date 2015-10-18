package ru.bekh.training.datastructures.list;

import ru.bekh.training.datastructures.Collection;

public interface List<T> extends Collection<T> {
    T get(int position);

    void append(T element);

    void insertAfter(int position, T element);

    void prepend(T element);

    void set(int position, T element);

    void remove(int position);

    int length();

    @Override
    default void add(T element) {
        append(element);
    }

    @Override
    default boolean isEmpty() {
        return length() == 0;
    }

    @Override
    default boolean remove(T element) {
        for (int position = 0; position < length(); ++position) {
            if (get(position).equals(element)) {
                remove(position);
                return true;
            }
        }

        return false;
    }
}
