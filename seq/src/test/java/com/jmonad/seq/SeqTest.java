package com.jmonad.seq;

import org.junit.Test;

public class SeqTest {

  @Test public void sampleTest() {
    Seq<Boolean> names = new Seq(true, true, false, null).compact();
    System.out.println(names.toArrayList());
  }
}
