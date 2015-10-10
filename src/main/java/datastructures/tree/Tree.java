package ru.bekh.training.datastructures.tree;

import java.util.Iterator;
import java.util.List;

interface Tree<T extends Comparable<T>> {
    enum IterationStrategy {
        LEVEL_ORDER,
        PREORDER,
        INORDER
    }

    void add(T element);

    default void add(T... elements) {
        for (T e : elements) {
            add(e);
        }
    }

    default void add(List<T> elements) {
        T[] a = (T[]) new Comparable[elements.size()];
        add(elements.toArray(a));
    }

    boolean remove(T element);
    int removeAll(T element);
    int find(T element);
    boolean contains(T element);
    int height();
    boolean isEmpty();

    Iterator<T> iterator(IterationStrategy strategy);

}
