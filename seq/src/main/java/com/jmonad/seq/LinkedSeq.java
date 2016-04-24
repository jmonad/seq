package com.jmonad.seq;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lucas on 24/04/16.
 */
public class LinkedSeq<T> extends Seq<T> {

  public LinkedSeq() {
    this.list = new LinkedList<>();
  }

  @SafeVarargs
  public LinkedSeq(T... params) {
    this();
    this.addAll(params);
  }

  public LinkedSeq(List<T> list) {
    this();
    this.addAll(list);
  }
}
