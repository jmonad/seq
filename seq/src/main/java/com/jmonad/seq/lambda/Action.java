package com.jmonad.seq.lambda;

public interface Action<T> {
  void call(T param);
}
