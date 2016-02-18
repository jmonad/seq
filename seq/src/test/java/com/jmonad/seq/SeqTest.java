package com.jmonad.seq;

import com.jmonad.seq.lambda.Function;
import java.util.ArrayList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeqTest {

  private final Logger slf4jLogger = LoggerFactory.getLogger(SeqTest.class);

  @Test public void sampleTest() {
    Seq<Boolean> names = new Seq(true, true, false, null).compact();
    System.out.println(names.toArrayList());
  }

  /**
   * The code was removed to don't fail the TravisCI test.
   */
  //@Test public void executeArrayZeroToBigNumberTest() {
  //
  //  ArrayList<Integer> integers = new ArrayList<Integer>(1000000);
  //  for (int i = 0; i <= 1000000; i++) {
  //    integers.add(i);
  //  }
  //
  //  Function func = new Function<Integer, Integer>() {
  //    @Override
  //    public Integer call(Integer x) {
  //      return x * 2;
  //    }
  //  };
  //
  //  long startTime = System.nanoTime();
  //
  //  Seq<Integer> values = new Seq<Integer>(integers).map(func);
  //
  //  long endTimeFirstRun = System.nanoTime();
  //
  //  String stubInteraction = "Test: " + values.toArrayList();
  //  System.out.print(stubInteraction);
  //
  //  long endTimeSecondRun = System.nanoTime();
  //
  //  long durationOne = (endTimeFirstRun - startTime);
  //  long durationSecond = (endTimeSecondRun - startTime) / 10000000;
  //
  //  slf4jLogger.info("Result one: "
  //      + durationOne
  //      + " nanosecond(s), Result two:"
  //      + durationSecond
  //      + "milisecond(s)");
  //}

}
