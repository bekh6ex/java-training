package ru.bekh.training.datastructures.list;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ArrayListTest {

    private final ArrayList<Integer> arrayList = new ArrayList<>();

    @Test
    public void canAddAndGetElementBack() {
        arrayList.set(0, 1);
        assertThat(arrayList.get(0), is(equalTo(1)));
        arrayList.set(1, 2);
        assertThat(arrayList.get(1), is(equalTo(2)));
    }

    @Test
    public void canSetElementInAnyPositivePosition() {
        arrayList.set(1, 2);

        assertThat(arrayList.get(1), is(equalTo(2)));
        assertThat(arrayList.get(0), is(nullValue()));
    }

    @Test
    public void canSetElementsInAnyOrder() {

        arrayList.set(1, 2);
        arrayList.set(0, 1);

        assertThat(arrayList.get(1), is(equalTo(2)));
        assertThat(arrayList.get(0), is(equalTo(1)));
    }

    @Test
    public void remove_CanNotGetThisElementAnymore()
    {
        arrayList.set(0, 1);

        arrayList.remove(0);

        assertThat(arrayList.get(0), is(nullValue()));
    }

    @Test
    public void set_LengthIsChanged()
    {
        assertThat(arrayList.length(), is(equalTo(0)));

        arrayList.set(0, 1);

        assertThat(arrayList.length(), is(equalTo(1)));
    }

    @Test
    public void remove_LengthIsChanged()
    {
        arrayList.set(0, 1);
        assertThat(arrayList.length(), is(equalTo(1)));

        arrayList.remove(0);

        assertThat(arrayList.length(), is(equalTo(0)));
    }

    @Test
    public void removeLast_HasEmptyElementsInTheMiddle_ShouldStripOffThoseElements()
    {
        arrayList.set(2, 1);
        assertThat(arrayList.length(), is(equalTo(3)));

        arrayList.remove(2);

        assertThat(arrayList.length(), is(equalTo(0)));
    }

    @Test
    public void removeElementsInTheMiddle_LengthShouldNotChange()
    {
        arrayList.set(1, 1);
        arrayList.set(2, 1);
        arrayList.set(3, 1);
        assertThat(arrayList.length(), is(equalTo(4)));

        arrayList.remove(2);
        arrayList.remove(1);

        assertThat(arrayList.length(), is(equalTo(4)));
    }

    @Test
    public void append_CanGetElement() {
        arrayList.append(1);
        arrayList.append(2);

        assertThat(arrayList.get(0), is(equalTo(1)));
        assertThat(arrayList.get(1), is(equalTo(2)));
    }

    @Test
    public void prepend_CanGetElement() {
        arrayList.prepend(1);
        assertThat(arrayList.get(0), is(equalTo(1)));

        arrayList.prepend(2);
        assertThat(arrayList.get(0), is(equalTo(2)));
        assertThat(arrayList.get(1), is(equalTo(1)));
    }
}