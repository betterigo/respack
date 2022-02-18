package io.github.betterigo.respack.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="respack.filter.settings")
public class PackSettings {
	//是否启用 默认为true
	private boolean enable = true;

	private String ignorePaths="";
	
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
}
