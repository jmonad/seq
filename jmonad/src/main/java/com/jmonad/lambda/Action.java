package com.jmonad.lambda;

public interface Action<T> {
	void call(T param);
}
