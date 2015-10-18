package ru.bekh.training.datastructures;

import java.util.List;

public interface Collection<T> extends Iterable<T> {
    void add(T element);

    @SuppressWarnings("unchecked")
    default void add(T... elements) {
        for (T e : elements) {
            add(e);
        }
    }

    @SuppressWarnings("unchecked")
    default void add(List<T> elements) {
        elements.forEach(this::add);
    }

    boolean remove(T element);

    default int removeAll(T element) {
        int count = 0;
        while (remove(element)) {
            count++;
        }
        return count;
    }

    default int find(T element) {
        int count = 0;
        for (T e : this) {
            if (e.equals(element)) {
                count++;
            }
        }

        return count;
    }

    default boolean contains(T element) {
        for (T e : this) {
            if (e.equals(element)) {
                return true;
            }
        }

        return false;
    }

    boolean isEmpty();
}
