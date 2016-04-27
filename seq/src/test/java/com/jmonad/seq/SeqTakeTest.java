package com.jmonad.seq;

import org.junit.Test;

public class SeqTakeTest {

  private Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private Seq<Integer> elements = new Seq<Integer>(numbers);

  @Test public void takeListElementsTest() {
    assert elements.take(5).toArrayList().toString().equals("[1, 2, 3, 4, 5]");
  }

  @Test public void takeElementsWithNoAmountTest() {
    assert elements.take(0).toArrayList().toString().equals("[]");
  }

  @Test public void takeElementsWithNegativeAmountTest() {
    assert elements.take(-1).toArrayList().toString().equals("[]");
  }

  @Test public void takeElementsWithAmountHigherThanListSizeTest() {
    assert elements.take(20).toArrayList().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
  }
}
