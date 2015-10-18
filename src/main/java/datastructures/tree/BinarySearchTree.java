package ru.bekh.training.datastructures.tree;

import com.sun.istack.internal.NotNull;
import ru.bekh.training.datastructures.queue.LinkedListQueue;
import ru.bekh.training.datastructures.queue.Queue;
import ru.bekh.training.datastructures.stack.LinkedListStack;
import ru.bekh.training.datastructures.stack.Stack;

import java.util.Iterator;
import java.util.Objects;

import static java.lang.Integer.max;

public class BinarySearchTree<T extends Comparable<T>> implements Tree<T> {


    class Node {
        private final T value;
        private Node left;
        private Node right;

        public Node(T value) {
            Objects.requireNonNull(value, "Node value can not be null");
            this.value = value;
        }

        private Node treeToSearchForTheElement(T element) {
            return element.compareTo(value) <= 0 ? left : right;
        }

        public boolean contains(T element) {
            if (value.equals(element)) {
                return true;
            }

            Node subTree = treeToSearchForTheElement(element);
            return subTree != null && subTree.contains(element);
        }

        public int find(T element) {
            int result = 0;
            if (value.equals(element)) {
                ++result;
            }

            Node subTree = treeToSearchForTheElement(element);
            if (subTree != null) {
                result += subTree.find(element);
            }

            return result;
        }


        private boolean hasNoChildren() {
            return right == null && left == null;
        }

        private boolean hasExactlyOneChild() {
            return right == null ^ left == null ;
        }

        private Node thatSingleChild() {
            if (!hasExactlyOneChild()) {
                throw new RuntimeException("Invalid call");
            }
            return right == null ? left : right;
        }

        public void addToSubtree(T element) {
            if (element.compareTo(value) <= 0) {
                if (left == null) {
                    left = new Node(element);
                } else {
                    left.addToSubtree(element);
                }
            } else {
                if (right == null) {
                    right = new Node(element);
                } else {
                    right.addToSubtree(element);
                }
            }
        }

        public boolean remove(T element) {
            if (right != null && right.value.equals(element)) {
                right = right.getReplacementForRemoval();
                return true;
            }else if (left != null && left.value.equals(element)) {
                left = left.getReplacementForRemoval();
                return true;
            } else {
                Node treeToSearchForTheElement = treeToSearchForTheElement(element);
                return treeToSearchForTheElement != null && treeToSearchForTheElement.remove(element);
            }
        }

        public Node getReplacementForRemoval() {
            if (hasNoChildren()) {
                return null;
            } else if (hasExactlyOneChild()) {
                return thatSingleChild();
            } else {
                Node replacement = right.getMinNode();
                right.remove(replacement.value);

                if (replacement != right) {
                    replacement.right = right;
                }

                replacement.left = left;

                return replacement;
            }
        }

        private Node getMinNode() {
            return left == null ? this : left.getMinNode();
        }

        @Override
        public String toString() {
            return  (left != null ? left.value : "-") + "(" + value + ")" + (right != null ? right.value : "-");
        }

        public int height() {
            int leftHeight = subtreeHeight(left);
            int rightHeight = subtreeHeight(right);

            return max(leftHeight, rightHeight) + 1;
        }

        private int subtreeHeight(Node node) {
            return node == null ? 0 :node.height();
        }
    }

    private Node root;

    class InOrderIterator implements Iterator {

        private Node root;
        final Stack<Node> backTrace = new LinkedListStack<>();
        Node lastVisitedItem = null;
        private boolean initialized = false;

        public InOrderIterator() {
            tryToInitializeIfNeeded();
        }

        private void tryToInitializeIfNeeded() {
            if (!initialized && BinarySearchTree.this.root != null) {
                root = BinarySearchTree.this.root;
                backTrace.push(root);
                movePointerToMostLeftNode();
                initialized = true;
            }
        }

        @Override
        public boolean hasNext() {
            tryToInitializeIfNeeded();
            if (!initialized) {
                return false;
            }
            updateIterationPositionIfNeeded();

            return !backTrace.isEmpty();
        }

        @Override
        public T next() {
            tryToInitializeIfNeeded();
            updateIterationPositionIfNeeded();

            Node currentItem = backTrace.pop();
            if (currentItem.right != null) {
                Node mostLeftTree = currentItem.right;
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
            Node mostLeftTree = backTrace.top().left;
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
        private final Node root;
        @NotNull
        private IteratorState toDoState = IteratorState.DATA_RETURN;
        private PreOrderIterator leftIterator;
        private PreOrderIterator rightIterator;

        public PreOrderIterator() {
            root = BinarySearchTree.this.root;
        }

        public PreOrderIterator(Node root) {
            this.root = root;
        }

        @Override
        public boolean hasNext() {
            boolean result;
            switch (toDoState) {
                case DATA_RETURN:
                    result = root != null;
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

        private final Queue<Node> queue = new LinkedListQueue<>();
        private final BinarySearchTree<T> root = BinarySearchTree.this;

        public DepthFirstIterator() {
            if (!isEmpty()) {
                queue.enqueue(BinarySearchTree.this.root);
            }
        }

        @Override
        public boolean hasNext() {
            return !root.isEmpty() && !queue.isEmpty();
        }

        @Override
        public T next() {
            Node currentItem = queue.dequeue();
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
        if (root == null) {
            root = new Node(element);
        } else {
            root.addToSubtree(element);
        }
    }

    @Override
    public boolean remove(T element) {
        if (isEmpty()) {
            return false;
        }

        if (root.value.equals(element)) {
            root = root.getReplacementForRemoval();
            return true;
        }

        return root.remove(element);

    }

    @Override
    public int find(T element) {
        return isEmpty() ? 0 : root.find(element);
    }

    @Override
    public boolean contains(T element) {
        return !isEmpty() && root.contains(element);
    }


    @Override
    public int height() {
        if (isEmpty()) {
            return 0;
        }


        return root.height();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override @SuppressWarnings("unchecked")
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
