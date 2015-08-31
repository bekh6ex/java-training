package ru.bekh.training.datastructures.stack;

public class LinkedListStack<T> implements Stack<T> {
    private class Node {
        Node prev;
        T value;

        public Node(Node prev, T value) {
            this.prev = prev;
            this.value = value;
        }
    }

    Node head;

    @Override
    public void push(T element) {
        head = new Node(head, element);
    }

    @Override
    public T pop() {
        Node current = head;
        head = current.prev;
        return current.value;
    }

    @Override
    public T top() {
        return head.value;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
