package com.jmonad.seq;

import org.junit.Test;

public class SeqDropTest {

    @Test public void dropListElementsTest() {
        Integer[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        Seq<Integer> elements = new Seq<Integer>(numbers);

        assert elements.drop(5).toArrayList().toString().equals("[6, 7, 8, 9, 10]");
        assert elements.drop(0).toArrayList().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
        assert elements.drop(20).toArrayList().toString().equals("[]");
    }
}
