package com.jmonad.seq;

import com.jmonad.seq.lambda.Function;

import org.junit.Test;

public class SeqSomeTest {

  private Integer[] numbers = {100, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

  private Function trueFunc = new Function<Boolean, Integer>() {
      @Override public Boolean call(Integer param) {
          return param > 10;
      }
  };

  private Function falseFunc = new Function<Boolean, Integer>() {
      @Override public Boolean call(Integer param) {
          return param > 100;
      }
  };

  @Test public void expectTrueFromSomeFunctionTest() {
      assert new Seq<>(numbers).some(trueFunc);
  }

  @Test public void expectFalseFromSomeFunctionTest() {
      assert !new Seq<>(numbers).some(falseFunc);
  }
}
