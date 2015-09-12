package ru.bekh.training.datastructures.tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BinarySearchTreeTest {

    BinarySearchTree<Integer> tree = new BinarySearchTree<>();

    @Test
    public void add_SingleNumber_CanFindThatNumber()
    {
        tree.add(1);

        assertThat(tree.contains(1), is(true));
    }

    @Test
    public void contains_NothingAdded_ShouldNotFindAnything()
    {
        assertThat(tree.contains(1), is(false));
    }

    @Test
    public void add_MultipleNumbersAdded_CanFindAll() {
        tree.add(1);
        tree.add(2);
        tree.add(3);

        assertThat(tree.contains(1), is(true));
        assertThat(tree.contains(2), is(true));
        assertThat(tree.contains(3), is(true));
    }

    @Test
    public void find_MultipleSameElementsAdded_ReturnsCountOfThatElements()
    {
        tree.add(1);
        tree.add(1);
        tree.add(1);

        assertThat(tree.find(1), is(equalTo(3)));
    }

    @Test
    public void find_MultipleSameAndSomeOfDifferentElementsAdded_ReturnsCountOfSameElements()
    {
        tree.add(1);
        tree.add(1);
        tree.add(2);
        tree.add(3);

        assertThat(tree.find(1), is(equalTo(2)));
    }

    @Test
    public void iterateInOrder_AddedInReversedOrder_TraversesInorder()
    {
        tree.add(3, 2, 1);

        Iterator<Integer> iterator = tree.iterator(Tree.IterationStrategy.INORDER);

        List<Integer> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);

        List<Integer> expectedList = new ArrayList<>(asList(1,2,3));
        assertThat(list, is(equalTo(expectedList)));
    }

    @Test
    public void iterateInOrder_AddedInStraightOrder_TraversesInorder()
    {
        tree.add(1 ,2, 3);

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
        BinarySearchTree<Integer>.InOrderIterator iterator = (BinarySearchTree<Integer>.InOrderIterator)tree.iterator(Tree.IterationStrategy.INORDER);
        tree.add(1);
        assertThat(iterator.next(), is(equalTo(1)));
        assertThat(iterator.hasNext(), is(false));
        tree.add(4, 3, 2);
        assertThat(iterator.next(), is(equalTo(2)));
        assertThat(iterator.next(), is(equalTo(3)));
        assertThat(iterator.next(), is(equalTo(4)));
        assertThat(iterator.hasNext(), is(false));
        tree.add(6, 7);
        assertThat(iterator.next(), is(equalTo(6)));
        assertThat(iterator.hasNext(), is(true));
        tree.add(5);
        assertThat(iterator.next(), is(equalTo(7)));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void iterateLevelOrder_SingleItem_IteratesIt()
    {
        tree.add(1);

        checkThat(tree.iterator(Tree.IterationStrategy.LEVEL_ORDER)).iteratesInFollowingOrder(1);
    }

    @Test
    public void iterateLevelOrder_RootNodeHasTwoChildren_IteratesIt()
    {
        tree.add(2,1,3);

        checkThat(tree.iterator(Tree.IterationStrategy.LEVEL_ORDER)).iteratesInFollowingOrder(2,1,3);
    }

    @Test
    public void iterateLevelOrder_ThreeLevelTree_IteratesIt()
    {
        tree.add(4,2,6,1,3,5,7);

        checkThat(tree.iterator(Tree.IterationStrategy.LEVEL_ORDER)).iteratesInFollowingOrder(4,2,6,1,3,5,7);
    }


    private <T1> IteratorTestWrapper<T1> checkThat(Iterator<T1> iterator) {
        return new IteratorTestWrapper(iterator);
    }

    private static class IteratorTestWrapper<T> implements Iterator<T> {

        private final Iterator<T> iterator;

        public IteratorTestWrapper(Iterator<T> iterator) {
            this.iterator = iterator;
        }

        public void iteratesInFollowingOrder(T... items) {
            List<T> list = new ArrayList<>();
            this.iterator.forEachRemaining(list::add);

            List<T> expectedList = new ArrayList<>(asList(items));
            assertThat(list, is(equalTo(expectedList)));
        }

        public void iteratesInFollowingOrder(List<T> items) {
            iteratesInFollowingOrder((T[]) items.toArray());
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
