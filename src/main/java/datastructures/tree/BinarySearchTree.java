package ru.bekh.training.datastructures.tree;

import com.sun.istack.internal.NotNull;
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

    enum IteratorState { DATA_RETURN, LEFT_TRAVERSAL, RIGHT_TRAVERSAL }

    abstract class TreeIterator implements Iterator {
        @Override
        abstract public boolean hasNext();

        @Override
        abstract public T next();
    }

    class NullIterator extends TreeIterator {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }
    }

    class PreOrderIterator extends TreeIterator {
        private final BinarySearchTree<T> root;
        @NotNull
        private IteratorState toDoState = IteratorState.DATA_RETURN;
        private PreOrderIterator leftIterator;
        private PreOrderIterator rightIterator;

        public PreOrderIterator() {
            root = BinarySearchTree.this;
        }

        public PreOrderIterator(BinarySearchTree<T> root) {
            this.root = root;
        }

        @Override
        public boolean hasNext() {
            boolean result;
            switch (toDoState) {
                case DATA_RETURN:
                    result = root.value != null;
                    break;
                case RIGHT_TRAVERSAL:
                    result = rightIterator().hasNext();
                    break;
                case LEFT_TRAVERSAL:
                    result = leftIterator().hasNext() || rightIterator().hasNext();
                    break;
                default:
                    throw new RuntimeException("Can`t happen");
            }

            return result;
        }

        private TreeIterator rightIterator() {
            if (rightIterator == null) {
                if (root.right != null) {
                    rightIterator = new PreOrderIterator(root.right);
                } else {
                    return new NullIterator();
                }
            }
            return rightIterator;
        }

        private TreeIterator leftIterator() {
            if (leftIterator == null) {
                if (root.left != null) {
                    leftIterator = new PreOrderIterator(root.left);
                } else {
                    return new NullIterator();
                }
            }
            return leftIterator;
        }

        @Override
        public T next() {
            T result;
            switch (toDoState) {
                case DATA_RETURN:
                    toDoState = IteratorState.LEFT_TRAVERSAL;
                    result = root.value;
                    break;
                case RIGHT_TRAVERSAL:
                    result = rightIterator().next();
                    break;
                case LEFT_TRAVERSAL:
                    if (leftIterator().hasNext()) {
                        result = leftIterator().next();
                    } else {
                        toDoState = IteratorState.RIGHT_TRAVERSAL;
                        result = next();
                    }
                    break;
                default:
                    throw new RuntimeException("Can`t happen");
            }

            return result;
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
            case LEVEL_ORDER:
                iterator = new DepthFirstIterator();
                break;
            case PREORDER:
                iterator = new PreOrderIterator();
                break;
            default:
                throw new RuntimeException("Unknown strategy");
        }
        return iterator;
    }


}
