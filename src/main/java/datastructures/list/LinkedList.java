package ru.bekh.training.datastructures.list;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class LinkedList<T> implements List<T> {

    private class Node {
        T value;
        Node next;

        public Node(T value) {
            this.value = value;
        }

        Node getLast() {
            return next == null ? this : this.getLast();
        }
    }

    Node head;

    @Override
    public T get(int position) {
        Node targetNode;
        Node node = head;
        for (int i = 0; ; ++i) {
            if (node == null) {
                return null;
            }

            if (i == position) {
                targetNode = node;
                break;
            }
            if (node.next == null) {
                return null;
            }

            node = node.next;
        }

        return targetNode.value;
    }

    @Override
    public void append(T element) {
        if (head == null) {
            head = new Node(element);
            return;
        }

        head.getLast().next = this.new Node(element);
    }

    @Override
    public void insertAfter(int position, T element) {
        Node currentNode = getNode(0);
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
        Node node = getNodeCreating(position);
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
        int length = 0;
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
    private Node getNodeCreating(int position) {
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
