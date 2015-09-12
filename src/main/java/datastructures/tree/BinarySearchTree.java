package ru.bekh.training.datastructures.tree;

import ru.bekh.training.datastructures.stack.LinkedListStack;
import ru.bekh.training.datastructures.stack.Stack;

import java.util.Iterator;

public class BinarySearchTree<T extends Comparable<T>> implements Tree<T> {

    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    private class InOrderIterator implements Iterator {

        private final BinarySearchTree<T> root = BinarySearchTree.this;
        Stack<BinarySearchTree<T>> parents = new LinkedListStack<>();
        BinarySearchTree<T> currentItem = null;

        public InOrderIterator() {
            BinarySearchTree<T> mostLeftTree = root;
            while (mostLeftTree != null) {
                parents.push(mostLeftTree);
                mostLeftTree = mostLeftTree.left;
            }
        }

        @Override
        public boolean hasNext() {
            updateIterationPositionIfNeeded();

            return !parents.isEmpty();
        }

        @Override
        public T next() {
            updateIterationPositionIfNeeded();

            currentItem = parents.pop();
            if (currentItem.right != null) {
                BinarySearchTree<T> mostLeftTree = currentItem.right;
                while (mostLeftTree != null) {
                    parents.push(mostLeftTree);
                    mostLeftTree = mostLeftTree.left;
                }
            }

            return currentItem.value;
        }


        private void updateIterationPositionIfNeeded() {
            if (parents.isEmpty() && currentItem.right != null) {
                BinarySearchTree<T> targetNode, node;
                targetNode = currentItem.right;
                node = root;
                parents.push(node);
                do {
                    node = node.right;
                    parents.push(node);
                } while (node != targetNode);

                BinarySearchTree<T> mostLeftTree = parents.top().left;
                while (mostLeftTree != null) {
                    parents.push(mostLeftTree);
                    mostLeftTree = mostLeftTree.left;
                }
            }
        }
    }

    @Override
    public void add(T element) {
        if (value == null) {
            value = element;
            return;
        }

        if (element.compareTo(value) <= 0) {
            leftTree().add(element);
        } else {
            rightTree().add(element);
        }
    }

    private BinarySearchTree<T> leftTree() {
        if (left == null) {
            left = new BinarySearchTree<>();
        }
        return left;
    }

    private BinarySearchTree<T> rightTree() {
        if (right == null) {
            right = new BinarySearchTree<>();
        }
        return right;
    }

    @Override
    public boolean remove(T element) {
        return false;
    }

    @Override
    public int removeAll(T element) {
        return 0;
    }

    @Override
    public int find(T element) {
        if (isEmpty()) {
            return 0;
        }

        int result = 0;
        if (value == element) {
            ++result;
        }

        BinarySearchTree<T> treeToSearchForTheElement = treeToSearchForTheElement(element);
        if (treeToSearchForTheElement != null) {
            result += treeToSearchForTheElement.find(element);
        }

        return result;
    }

    @Override
    public boolean contains(T element) {
        if (isEmpty()) {
            return false;
        }
        if (value == element) {
            return true;
        }

        BinarySearchTree<T> treeToSearch = treeToSearchForTheElement(element);
        return treeToSearch != null && treeToSearch.contains(element);
    }

    private BinarySearchTree<T> treeToSearchForTheElement(T element) {
        return element.compareTo(value) <= 0 ? left : right;
    }


    @Override
    public int height() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }

    @Override
    public Iterator<T> iterator(IterationStrategy strategy) {
        Iterator<T> iterator;
        switch (strategy) {
            case INORDER:
                iterator = new InOrderIterator();
                break;
            default:
                throw new RuntimeException("Unknown strategy");
        }
        return iterator;
    }


}
