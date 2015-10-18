package ru.bekh.training.datastructures.list;

public class ArrayListTest extends ListTest {

    private ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected List<Integer> list() {
        return list;
    }
}
