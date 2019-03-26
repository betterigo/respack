package com.troila.cloud.respack.core;

public interface ResultPackager<T>{
	PackEntity pack(RespAttrs attrs, T data);
}
