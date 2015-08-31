package ru.bekh.training.datastructures.stack;

public class ArrayStackTest extends StackTest {
    private ArrayStack<Integer> stack = new ArrayStack<>();

    @Override
    protected Stack<Integer> stack() {
        return stack;
    }
}