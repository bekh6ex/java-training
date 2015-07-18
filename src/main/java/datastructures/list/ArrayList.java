package ru.bekh.training.datastructures.list;

public class ArrayList<T> implements List<T> {

    private T[] list = (T[]) new Object[0];

    public T get(int position) {
        if (isOutOfBounds(position)) {
            return null;
        }

        return list[position];
    }

    @Override
    public void append(T element) {
        set(list.length, element);
    }

    @Override
    public void insertAfter(int position, T element) {
        int newLength = Integer.max(position + 2, list.length + 1);
        T[] newList = (T[]) new Object[newLength];
        int newElementPosition = position + 1;
        System.arraycopy(list, 0, newList, 0, Integer.min(newElementPosition, list.length));
        newList[newElementPosition] = element;
        if (newElementPosition < list.length) {
            System.arraycopy(list, newElementPosition, newList, newElementPosition + 1, list.length - (newElementPosition));
        }

        list = newList;
    }

    @Override
    public void prepend(T element) {
        insertAfter(-1, element);
    }

    public void set(int position, T element) {
        int newLength = Integer.max(list.length, position + 1);
        T[] newList = copyList(newLength);
        newList[position] = element;
        list = newList;
    }

    public void remove(int position) {
        T[] newList = (T[]) new Object[list.length-1];
        System.arraycopy(list, 0, newList, 0, position);
        System.arraycopy(list, position + 1, newList, position, list.length - (position + 1));
        list = newList;
    }

    private T[] copyList(int newLength) {
        T[] newList = (T[]) new Object[newLength];
        int copyLength = Integer.min(list.length, newLength);
        System.arraycopy(list, 0, newList, 0, copyLength);
        return newList;
    }

    public int length() {
        return list.length;
    }
    private boolean isOutOfBounds(int position) {
        return position > list.length - 1;
    }
}
