package ru.bekh.training.datastructures;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

abstract public class CollectionTest {

    abstract protected Collection<Integer> collection();

    @Test
    public void contains_NothingAdded_ShouldNotFindAnything()
    {
        assertThat(collection().contains(1), is(false));
    }

    @Test
    public void add_MultipleNumbersAdded_CanFindAll() {
        collection().add(1);
        collection().add(2);
        collection().add(3);

        assertThat(collection().contains(1), is(true));
        assertThat(collection().contains(2), is(true));
        assertThat(collection().contains(3), is(true));
    }

    @Test
    public void find_MultipleSameElementsAdded_ReturnsCountOfThatElements()
    {
        collection().add(1);
        collection().add(1);
        collection().add(1);

        assertThat(collection().find(1), is(equalTo(3)));
    }

    @Test
    public void find_MultipleSameAndSomeOfDifferentElementsAdded_ReturnsCountOfSameElements()
    {
        collection().add(1);
        collection().add(1);
        collection().add(2);
        collection().add(3);

        assertThat(collection().find(1), is(equalTo(2)));
    }

    @Test
    public void remove_OnlyElement_TreeIsEmpty() {
        collection().add(1);

        collection().remove(1);

        assertThat(collection().isEmpty(), is(true));
    }

    @Test
    public void remove_ExistingElement_ReturnsTrue() {
        collection().add(1);

        assertThat(collection().remove(1), is(true));
    }

    @Test
    public void remove_NonexistentElement_ReturnsTrue() {
        collection().add(1);

        assertThat(collection().remove(2), is(false));
    }

    @Test
    public void remove_RightLeafNode_RemovesIt()
    {
        collection().add(2, 1, 3);

        collection().remove(3);

        assertThat(collection().find(3), is(equalTo(0)));
    }

    @Test
    public void remove_LeftLeafNode_RemovesIt()
    {
        collection().add(2, 1, 3);

        collection().remove(1);

        assertThat(collection().find(1), is(equalTo(0)));
    }

    @Test
    public void removeAll_MultipleElementsAddedAndRemoved_ReturnsCountOfElementsRemoved()
    {
        collection().add(1, 1, 1);

        assertThat(collection().removeAll(1), is(equalTo(3)));
    }
}
