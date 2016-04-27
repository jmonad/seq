package com.jmonad.seq;

import org.junit.Test;

public class SeqDropTest {

  private Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private Seq<Integer> elements = new Seq<>(numbers);

  @Test public void dropListElementsTest() {
    assert elements.drop(5).toArrayList().toString().equals("[6, 7, 8, 9, 10]");
  }

  @Test public void dropElementsWithNoAmountTest() {
    assert elements.drop(0).toArrayList().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
  }

  @Test public void dropElementsWithNegativeAmountTest() {
    assert elements.drop(-1).toArrayList().toString().equals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
  }

  @Test public void dropElementsWithAmountHigherThanListSizeTest() {
    assert elements.drop(20).toArrayList().toString().equals("[]");
  }
}
