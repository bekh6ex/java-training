package ru.bekh.training.datastructures.tree;

import org.junit.Test;
import ru.bekh.training.datastructures.Collection;
import ru.bekh.training.datastructures.CollectionTest;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BinarySearchTreeTest extends CollectionTest {

    protected Collection<Integer> collection() {
        return tree;
    }

    private final BinarySearchTree<Integer> tree = new BinarySearchTree<>();

    @Test
    public void add_SingleNumber_CanFindThatNumber()
    {
        collection().add(1);

        assertThat(collection().contains(1), is(true));
    }

    @Test
    public void iterateInOrder_AddedInReversedOrder_TraversesInorder()
    {
        collection().add(3, 2, 1);

        Iterator<Integer> iterator = tree.iterator(Tree.IterationStrategy.INORDER);

        List<Integer> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);

        List<Integer> expectedList = new ArrayList<>(asList(1,2,3));
        assertThat(list, is(equalTo(expectedList)));
    }

    @Test
    public void iterateInOrder_AddedInStraightOrder_TraversesInorder()
    {
        collection().add(1, 2, 3);

        Iterator<Integer> iterator = tree.iterator(Tree.IterationStrategy.INORDER);

        checkThat(iterator).iteratesInFollowingOrder(1,2,3);
    }

    @Test
    public void iterateInOrder_ComplexTest_TraversesInorder()
    {
        for (int i = 1; i < 100; ++i) {
            BinarySearchTree<Integer> tree = new BinarySearchTree<>();

            ArrayList<Integer> orderedList = new ArrayList<>(
                    IntStream.rangeClosed(1, i).boxed().collect(Collectors.toList()));
            ArrayList<Integer> clone = (ArrayList<Integer>) orderedList.clone();
            Collections.shuffle(clone);
            tree.add(clone);

            Iterator<Integer> iterator = tree.iterator(Tree.IterationStrategy.INORDER);

            checkThat(iterator).iteratesInFollowingOrder(orderedList);
        }
    }

    @Test
    public void iterateInOrder_ItemsAddedWhileIterating_TraversesThisItems()
    {
        BinarySearchTree<Integer>.InOrderIterator iterator = (BinarySearchTree<Integer>.InOrderIterator) tree.iterator(Tree.IterationStrategy.INORDER);
        collection().add(1);
        assertThat(iterator.next(), is(equalTo(1)));
        assertThat(iterator.hasNext(), is(false));
        collection().add(4, 3, 2);
        assertThat(iterator.next(), is(equalTo(2)));
        assertThat(iterator.next(), is(equalTo(3)));
        assertThat(iterator.next(), is(equalTo(4)));
        assertThat(iterator.hasNext(), is(false));
        collection().add(6, 7);
        assertThat(iterator.next(), is(equalTo(6)));
        assertThat(iterator.hasNext(), is(true));
        collection().add(5);
        assertThat(iterator.next(), is(equalTo(7)));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void iterateLevelOrder_SingleItem_IteratesIt()
    {
        collection().add(1);

        checkThat(tree.iterator(Tree.IterationStrategy.LEVEL_ORDER)).iteratesInFollowingOrder(1);
    }

    @Test
    public void iterateLevelOrder_RootNodeHasTwoChildren_IteratesIt()
    {
        collection().add(2, 1, 3);

        checkThat(tree.iterator(Tree.IterationStrategy.LEVEL_ORDER)).iteratesInFollowingOrder(2,1,3);
    }

    @Test
    public void iterateLevelOrder_ThreeLevelTree_IteratesIt()
    {
        collection().add(4, 2, 6, 1, 3, 5, 7);

        checkThat(tree.iterator(Tree.IterationStrategy.LEVEL_ORDER)).iteratesInFollowingOrder(4,2,6,1,3,5,7);
    }

    @Test
    public void iteratePreOrder_SingleValue_IteratesIt()
    {
        collection().add(1);

        checkThat(tree.iterator(Tree.IterationStrategy.PREORDER)).iteratesInFollowingOrder(1);
    }

    @Test
    public void iteratePreOrder_TreeGoesOnlyOnTheLeft_IteratesIt()
    {
        collection().add(3, 2, 1);

        Iterator<Integer> iterator = tree.iterator(Tree.IterationStrategy.PREORDER);
        checkThat(tree.iterator(Tree.IterationStrategy.PREORDER)).iteratesInFollowingOrder(3,2,1);
        assertThat(iterator.next(), is(equalTo(3)));
        assertThat(iterator.next(), is(equalTo(2)));
        assertThat(iterator.next(), is(equalTo(1)));

        checkThat(tree.iterator(Tree.IterationStrategy.PREORDER)).iteratesInFollowingOrder(3,2,1);
    }


    @Test
    public void remove_MultipleItemsAddedAlmostHalfOfThemRemoved_IteratesInorderCorrectly()
    {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        int upperBoundary = 100000;
        int boundary = upperBoundary/2;
        ArrayList<Integer> orderedList = new ArrayList<>(
                IntStream.rangeClosed(1, upperBoundary).boxed().collect(Collectors.toList()));
        ArrayList<Integer> clone = (ArrayList<Integer>) orderedList.clone();
        Collections.shuffle(clone);
        tree.add(clone);

        new Random().ints(boundary, 1, boundary).forEach(tree::remove);

        IntStream.rangeClosed(boundary + 1, upperBoundary).forEach(e -> assertThat("Tree should contain " + e, tree.contains(e), is(true)));

        checkThat(tree.iterator(Tree.IterationStrategy.INORDER)).doesCorrectInorderIteration();
    }

    @Test
    public void height_EmptyTree_ReturnsZero() {
        assertThat(tree.height(), is(equalTo(0)));
    }

    @Test
    public void height_SimgleElement_ReturnsOne() {
        tree.add(1);

        assertThat(tree.height(), is(equalTo(1)));
    }

    @Test
    public void height_TwoElements_ReturnsTwo() {
        tree.add(1, 2);

        assertThat(tree.height(), is(equalTo(2)));
    }

    @Test
    public void height_ThreeLevelTree_ReturnsThree()
    {
        tree.add(4, 2, 6, 1, 3, 5, 7);

        assertThat(tree.height(), is(equalTo(3)));
    }

    @Test
    public void height_ElementsAddedInIncreasingOrder_ReturnsElementsCount()
    {
        tree.add(1, 2, 3, 4, 5, 6, 7);

        assertThat(tree.height(), is(equalTo(7)));
    }


    private <T1 extends Comparable<T1>> IteratorTestWrapper<T1> checkThat(Iterator<T1> iterator) {
        return new IteratorTestWrapper(iterator);
    }

    private static class IteratorTestWrapper<T extends Comparable<T>> implements Iterator<T> {

        private final Iterator<T> iterator;

        public IteratorTestWrapper(Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @SuppressWarnings("unchecked")
        public void iteratesInFollowingOrder(T... items) {
            iteratesInFollowingOrder(asList(items));
        }

        public void iteratesInFollowingOrder(List<T> items) {
            List<T> list = new ArrayList<>();
            this.iterator.forEachRemaining(list::add);

            List<T> expectedList = new ArrayList<>(items);
            assertThat(list, is(equalTo(expectedList)));
        }

        public void doesCorrectInorderIteration() {
            T prev = null;
            while (iterator.hasNext()) {
                T current = iterator.next();
                if (prev != null) {
                    assertThat(prev.compareTo(prev) <= 0, is(true));
                }

                prev = current;
            }
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            iterator.remove();
        }

        @Override
        public void forEachRemaining(Consumer action) {
            iterator.forEachRemaining(action);
        }
    }
}
