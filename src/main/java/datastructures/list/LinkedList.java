package ru.bekh.training.datastructures.list;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Iterator;

public class LinkedList<T> implements List<T> {

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node node = head;
            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                T next = node.value;
                node = node.next;
                return next;
            }
        };
    }

    private class Node {
        T value;
        Node next;

        public Node(T value) {
            this.value = value;
        }
    }

    Node head;

    @Override
    public T get(int position) {
        Node node = getNode(position);

        return node == null ? null : node.value;
    }

    @Override
    public void append(T element) {
        if (head == null) {
            head = new Node(element);
            return;
        }

        Node node = head;
        while (node.next != null) {
            node = node.next;
        }

        node.next = this.new Node(element);
    }

    @Override
    public void insertAfter(int position, T element) {
        Node currentNode = getNodeCreatingNullNodes(position);
        Node newNode = new Node(element);
        newNode.next = currentNode.next;
        currentNode.next = newNode;
    }

    @Override
    public void prepend(T element) {
        Node second = head;
        head = new Node(element);
        head.next = second;
    }

    @Override
    public void set(int position, T element) {
        Node node = getNodeCreatingNullNodes(position);
        node.value = element;
    }

    @Override
    public void remove(int position) {
        if (head == null) {
            return;
        }

        if (position == 0) {
            head = head.next;
        } else {
            Node prevNode = getNode(position - 1);
            if (prevNode == null) {
                return;
            }
            prevNode.next = prevNode.next != null ? prevNode.next.next : null;
        }
    }

    @Override
    public int length() {
        Node node = head;
        int length;
        for (int i = 0; ; ++i) {
            if (node == null) {
                length = i;
                break;
            }

            node = node.next;
        }

        return length;
    }

    @Nullable
    private Node getNode(int position) {
        Node node = head;
        for (int i = 0; i < position; ++i) {
            if (node == null) {
                return null;
            }

            node = node.next;
        }

        return node;
    }

    @NotNull
    private Node getNodeCreatingNullNodes(int position) {
        if (head == null) {
            head = new Node(null);
        }

        Node node = head;
        for (int i = 0; i < position; ++i) {
            if (node.next == null) {
                node.next = new Node(null);
            }
            node = node.next;
        }

        return node;
    }
}
