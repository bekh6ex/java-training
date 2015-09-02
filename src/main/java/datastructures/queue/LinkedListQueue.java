package ru.bekh.training.datastructures.queue;

public class LinkedListQueue<T> implements Queue<T> {

    private class Node {
        public final T value;
        public Node next;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;

    @Override
    public void enqueue(T element) {
        Node node = new Node(element);
        if (tail != null) {
            tail.next = node;
        }
        if (head == null) {
            head = node;
        }

        tail = node;
    }

    @Override
    public T dequeue() {
        Node newHead = head.next;
        T result = head.value;
        head = newHead;
        return result;
    }

    @Override
    public T peek() throws NullPointerException {
        return head.value;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
