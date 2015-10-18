package ru.bekh.training.datastructures.tree;

import ru.bekh.training.datastructures.Collection;

import java.util.Iterator;

interface Tree<T extends Comparable<T>> extends Collection<T> {
    enum IterationStrategy {
        LEVEL_ORDER,
        PREORDER,
        INORDER;
    }

    int height();

    Iterator<T> iterator(IterationStrategy strategy);

    @Override
    default Iterator<T> iterator() {
        return iterator(IterationStrategy.INORDER);
    }
}
