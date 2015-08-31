package ru.bekh.training.datastructures.stack;

public class ArrayStack<T> implements Stack<T> {
    public static final int STARTING_ARRAY_SIZE = 1;
    private T[] list = (T[]) new Object[STARTING_ARRAY_SIZE];
    private int newElementIndex = 0;

    @Override
    public void push(T element) {

        if (list.length == newElementIndex) {
            T[] tempList = (T[]) new Object[list.length * 2];
            System.arraycopy(list, 0, tempList, 0, list.length);
            list = tempList;
        }

        list[newElementIndex] = element;
        newElementIndex++;
    }

    @Override
    public T pop() {
        newElementIndex--;
        return list[newElementIndex];
    }

    @Override
    public T top() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return newElementIndex == 0;
    }
}
