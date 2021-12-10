package io.github.betterigo.respack.core;

import java.io.Serializable;

public abstract class PackEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6155834273471261539L;
	
	public abstract void setData(Object obj);
	
	public abstract Object getData();
}
