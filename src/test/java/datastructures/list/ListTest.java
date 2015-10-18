package ru.bekh.training.datastructures.list;

import org.junit.Test;
import ru.bekh.training.datastructures.CollectionTest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

abstract public class ListTest extends CollectionTest {

    @Test
    public void canAddAndGetSingleElementBack() {
        list().set(0, 1);
        assertThat(list().get(0), is(equalTo(1)));
    }

    @Test
    public void canAddAndGetElementBack() {
        list().set(0, 1);
        assertThat(list().get(0), is(equalTo(1)));
        list().set(1, 2);
        assertThat(list().get(1), is(equalTo(2)));
    }

    @Test
    public void canSetElementInAnyPositivePosition() {
        list().set(1, 2);

        assertThat(list().get(1), is(equalTo(2)));
        assertThat(list().get(0), is(nullValue()));
    }

    @Test
    public void canSetElementsInAnyOrder() {

        list().set(1, 2);
        list().set(0, 1);

        assertThat(list().get(1), is(equalTo(2)));
        assertThat(list().get(0), is(equalTo(1)));
    }

    @Test
    public void remove_CanNotGetThisElementAnymore()
    {
        list().set(0, 1);

        list().remove(0);

        assertThat(list().get(0), is(nullValue()));
    }

    @Test
    public void set_LengthIsChanged()
    {
        assertThat(list().length(), is(equalTo(0)));

        list().set(0, 1);

        assertThat(list().length(), is(equalTo(1)));
    }

    @Test
    public void remove_LengthIsChanged()
    {
        list().set(0, 1);
        assertThat(list().length(), is(equalTo(1)));

        list().remove(0);

        assertThat(list().length(), is(equalTo(0)));
    }

    @Test
    public void removeLast_HasEmptyElementsInTheMiddle_LeavesNullElements()
    {
        list().set(2, 1);
        assertThat(list().length(), is(equalTo(3)));

        list().remove(2);

        assertThat(list().length(), is(equalTo(2)));
    }

    @Test
    public void removeElementsInTheMiddle_LengthShouldChange()
    {
        list().set(1, 1);
        list().set(2, 1);
        list().set(3, 1);
        assertThat(list().length(), is(equalTo(4)));

        list().remove(2);
        list().remove(1);

        assertThat(list().length(), is(equalTo(2)));
    }

    @Test
    public void append_CanGetElement() {
        list().append(1);
        list().append(2);

        assertThat(list().get(0), is(equalTo(1)));
        assertThat(list().get(1), is(equalTo(2)));
    }

    @Test
    public void prepend_CanGetElement() {
        list().prepend(1);
        assertThat(list().get(0), is(equalTo(1)));

        list().prepend(2);
        assertThat(list().get(0), is(equalTo(2)));
        assertThat(list().get(1), is(equalTo(1)));
    }

    @Test
    public void insertAfter_SingleElement_CanGetItBack() {
        list().set(0, 0);
        list().insertAfter(0, 1);

        assertThat(list().get(1), is(equalTo(1)));
    }

    @Test
    public void insertAfter_SeveralElements_InsertedInCorrectOrder()
    {
        list().set(0, 1);
        list().insertAfter(0, 2);
        list().insertAfter(0, 3);

        assertThat(list().get(1), is(equalTo(3)));
        assertThat(list().get(2), is(equalTo(2)));
    }

    @Test
    public void insertAfter_NonexistentElement_CreatesNullElementsBefore()
    {
        list().insertAfter(1, 2);

        assertThat(list().get(0), is(nullValue()));
        assertThat(list().get(1), is(nullValue()));
        assertThat(list().get(2), is(equalTo(2)));
    }

    abstract protected List<Integer> list();
}
