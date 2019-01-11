package com.troila.cloud.respack.config.settings;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="respack.filter.settings")
public class FilterSettings {
	//是否启用 默认为true
	private boolean enable = true;

	private String ignorePaths="";
	
	private int filterOrder = Integer.MAX_VALUE;

	private int maxCache = 1024;
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getIgnorePaths() {
		return ignorePaths;
	}

	public void setIgnorePaths(String ignorePaths) {
		this.ignorePaths = ignorePaths;
	}

	public int getFilterOrder() {
		return filterOrder;
	}

	public void setFilterOrder(int filterOrder) {
		this.filterOrder = filterOrder;
	}

	public int getMaxCache() {
		return maxCache;
	}

	public void setMaxCache(int maxCache) {
		this.maxCache = maxCache;
	}

	public List<String> getIgnorePathsList(){
		String[] arrayStr = ignorePaths.split(";");
		List<String> result = new ArrayList<>();
		for(String o:arrayStr) {
			if(o!=null && !"".equals(o))
			result.add(o);
		}
		return result;
	}
}
