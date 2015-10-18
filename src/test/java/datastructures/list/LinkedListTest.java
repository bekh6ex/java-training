package ru.bekh.training.datastructures.list;

public class LinkedListTest extends ListTest {


    private final LinkedList<Integer> list = new LinkedList<>();

    @Override
    protected List<Integer> list() {
        return list;
    }
}
