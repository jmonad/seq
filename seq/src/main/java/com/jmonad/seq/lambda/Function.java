package com.jmonad.seq.lambda;

public interface Function<Ret, T> {
	Ret call(T param);
}
