package io.github.betterigo.respack.core;

public interface RespAttrs {
	int getStatus();
	
	public void setExtInfo(String k,String v);
	
	public String getExtInfo(String k);
}
