package com.troila.cloud.respack.core;

public interface ResultPackager<T,R>{
	R pack(RespAttrs attrs, T data);
}
