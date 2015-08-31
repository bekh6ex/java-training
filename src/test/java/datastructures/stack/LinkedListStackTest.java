package ru.bekh.training.datastructures.stack;

public class LinkedListStackTest extends StackTest {

    @Override
    protected Stack<Integer> stack() {
        return new LinkedListStack<>();
    }
}