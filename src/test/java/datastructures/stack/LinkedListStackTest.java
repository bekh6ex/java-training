package ru.bekh.training.datastructures.stack;

public class LinkedListStackTest extends StackTest {

    private LinkedListStack<Integer> stack = new LinkedListStack<>();

    @Override
    protected Stack<Integer> stack() {
        return stack;
    }
}