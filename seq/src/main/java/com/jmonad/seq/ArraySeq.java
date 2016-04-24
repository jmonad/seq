package com.jmonad.seq;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lucas on 24/04/16.
 */
public class ArraySeq<T> extends Seq<T> {

  public ArraySeq() {
    this.list = new ArrayList<>();
  }

  @SafeVarargs
  public ArraySeq(T... params) {
    this();
    this.addAll(params);
  }

  public ArraySeq(List<T> list) {
    this();
    this.addAll(list);
  }
}
