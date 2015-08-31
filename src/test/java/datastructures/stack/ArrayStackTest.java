package ru.bekh.training.datastructures.stack;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArrayStackTest extends StackTest {
    private ArrayStack<Integer> stack = new ArrayStack<>();

    @Test
    public void isEmpty_EmptyStack_ReturnsTrue() {
        assertThat(stack.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void isEmpty_NonEmptyStack_ReturnsFalse() {
        stack.push(0);

        assertThat(stack.isEmpty(), is(equalTo(false)));
    }

    @Test
    public void isEmpty_PushedAndPoppedSomeElement_ReturnsTrue() {
        stack.push(0);
        stack.pop();

        assertThat(stack.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void pop_PushedOneElement_ReturnsThatElement()
    {
        stack.push(0);

        Integer element = stack.pop();

        assertThat(element, is(equalTo(0)));
    }

    @Test
    public void pop_PushedMultipleElements_ReturnsThatElementsInReversedOrder()
    {
        stack.push(0);
        stack.push(1);
        stack.push(2);

        Integer element1 = stack.pop();
        Integer element2 = stack.pop();
        Integer element3 = stack.pop();

        assertThat(new Integer[]{element1, element2, element3}, is(equalTo(new Integer[]{2, 1, 0})));
    }

    @Test
    public void top_PushedSomeElement_ReturnsThatElement()
    {
        stack.push(0);

        assertThat(stack.top(), is(equalTo(0)));
    }

    @Test
    public void top_PushedSomeElement_ReturnsThatElementEveryTime()
    {
        stack.push(0);

        assertThat(stack.top(), is(equalTo(0)));
        assertThat(stack.top(), is(equalTo(0)));
    }

    @Override
    protected Stack<Integer> stack() {
        return stack;
    }
}