package ru.bekh.training.datastructures.list;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ArrayListTest {

    private final ArrayList<Integer> arrayList = new ArrayList<>();

    @Test
    public void canAddAndGetElementBack() {
        arrayList.insert(0, 1);
        assertThat(arrayList.get(0), is(equalTo(1)));
        arrayList.insert(1, 2);
        assertThat(arrayList.get(1), is(equalTo(2)));
    }

    @Test
    public void canInsertElementToAnyPositivePosition() {
        arrayList.insert(1, 2);

        assertThat(arrayList.get(1), is(equalTo(2)));
        assertThat(arrayList.get(0), is(nullValue()));
    }

    @Test
    public void canInsertElementsInAnyOrder() {

        arrayList.insert(1, 2);
        arrayList.insert(0, 1);

        assertThat(arrayList.get(1), is(equalTo(2)));
        assertThat(arrayList.get(0), is(equalTo(1)));
    }

    @Test
    public void remove_CanNotGetThisElementAnymore()
    {
        arrayList.insert(0, 1);

        arrayList.remove(0);

        assertThat(arrayList.get(0), is(nullValue()));
    }

    @Test
    public void insert_LengthIsChanged()
    {
        assertThat(arrayList.length(), is(equalTo(0)));

        arrayList.insert(0, 1);

        assertThat(arrayList.length(), is(equalTo(1)));
    }

    @Test
    public void remove_LengthIsChanged()
    {
        arrayList.insert(0,1);
        assertThat(arrayList.length(), is(equalTo(1)));

        arrayList.remove(0);

        assertThat(arrayList.length(), is(equalTo(0)));
    }

    @Test
    public void removeLast_HasEmptyElementsInTheMiddle_ShouldStripOffThoseElements()
    {
        arrayList.insert(2, 1);
        assertThat(arrayList.length(), is(equalTo(3)));

        arrayList.remove(2);

        assertThat(arrayList.length(), is(equalTo(0)));
    }

    @Test
    public void removeElementsInTheMiddle_LengthShouldNotChange()
    {
        arrayList.insert(1, 1);
        arrayList.insert(2, 1);
        arrayList.insert(3, 1);
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