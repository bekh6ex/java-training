package ru.bekh.training.datastructures.tree;

import ru.bekh.training.datastructures.queue.LinkedListQueue;
import ru.bekh.training.datastructures.queue.Queue;
import ru.bekh.training.datastructures.stack.LinkedListStack;
import ru.bekh.training.datastructures.stack.Stack;

import java.util.Iterator;

public class BinarySearchTree<T extends Comparable<T>> implements Tree<T> {

    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    class InOrderIterator implements Iterator {

        private final BinarySearchTree<T> root = BinarySearchTree.this;
        Stack<BinarySearchTree<T>> backTrace = new LinkedListStack<>();
        BinarySearchTree<T> lastVisitedItem = null;

        public InOrderIterator() {
            backTrace.push(root);
            movePointerToMostLeftNode();
        }

        @Override
        public boolean hasNext() {
            updateIterationPositionIfNeeded();

            return !backTrace.isEmpty();
        }

        @Override
        public T next() {
            updateIterationPositionIfNeeded();

            BinarySearchTree<T> currentItem = backTrace.pop();
            if (currentItem.right != null) {
                BinarySearchTree<T> mostLeftTree = currentItem.right;
                while (mostLeftTree != null) {
                    backTrace.push(mostLeftTree);
                    mostLeftTree = mostLeftTree.left;
                }
            }

            lastVisitedItem = currentItem;

            return currentItem.value;
        }


        private void updateIterationPositionIfNeeded() {
            if (backTrace.isEmpty() && lastVisitedItem.right != null) {
                backTrace.push(lastVisitedItem.right);
                movePointerToMostLeftNode();
            }
        }

        private void movePointerToMostLeftNode() {
            BinarySearchTree<T> mostLeftTree = backTrace.top().left;
            while (mostLeftTree != null) {
                backTrace.push(mostLeftTree);
                mostLeftTree = mostLeftTree.left;
            }
        }
    }

    class DepthFirstIterator implements Iterator {

        private Queue<BinarySearchTree<T>> queue = new LinkedListQueue<>();
        private BinarySearchTree<T> root = BinarySearchTree.this;

        public DepthFirstIterator() {
            queue.enqueue(root);
        }

        @Override
        public boolean hasNext() {
            return !root.isEmpty() && !queue.isEmpty();
        }

        @Override
        public T next() {
            BinarySearchTree<T> currentItem = queue.dequeue();
            if (currentItem.left != null) {
                queue.enqueue(currentItem.left);
            }
            if (currentItem.right != null) {
                queue.enqueue(currentItem.right);
            }

            return currentItem.value;
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
            case DEPTH_FIRST:
                iterator = new DepthFirstIterator();
                break;
            default:
                throw new RuntimeException("Unknown strategy");
        }
        return iterator;
    }


}
