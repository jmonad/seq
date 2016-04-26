package com.jmonad.seq;

import org.junit.Test;

public class SeqTakeTest {

  @Test public void takeListElementsTest() {
    Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    Seq<Integer> elements = new Seq<Integer>(numbers);

    assert elements.take(5).toArrayList().toString().equals("[1, 2, 3, 4, 5]");
    assert elements.take(0).toArrayList().toString().equals("[]");
    assert elements.take(20).toArrayList().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
  }
}
