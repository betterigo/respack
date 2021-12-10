package io.github.betterigo.respack.core;

public interface ResultPackager<T,R>{
	R pack(RespAttrs attrs, T data);
}
