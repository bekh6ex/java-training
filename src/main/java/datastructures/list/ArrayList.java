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
        T[] newList = (T[]) new Object[list.length+1];
        System.arraycopy(list, 0, newList, 0, position + 1);
        newList[position + 1] = element;
        System.arraycopy(list, position + 1, newList, position + 2, list.length - (position + 1));

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
        list[position] = null;
        int newLength = 0;
        for (int i = list.length - 1; i >= 0; --i) {
            if (list[i] != null) {
                newLength = i + 1;
                break;
            }
        }

        list = copyList(newLength);
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
