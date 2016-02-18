package com.jmonad.lambda;

public interface BinaryFunction<Ret, T, U> {
	Ret call(T param1, U param2);
}
