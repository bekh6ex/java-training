package ru.bekh.training.datastructures.list;

import ru.bekh.training.datastructures.Collection;

public class LinkedListTest extends ListTest {


    private final LinkedList<Integer> list = new LinkedList<>();

    @Override
    protected List<Integer> list() {
        return list;
    }

    @Override
    protected Collection<Integer> collection() {
        return list();
    }
}
