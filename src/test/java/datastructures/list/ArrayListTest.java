package ru.bekh.training.datastructures.list;

import ru.bekh.training.datastructures.Collection;

public class ArrayListTest extends ListTest {

    private ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected List<Integer> list() {
        return list;
    }

    @Override
    protected Collection<Integer> collection() {
        return list();
    }
}
