package io.github.betterigo.respack.config;

import io.github.betterigo.respack.config.settings.DefaultPackPatternConfigAdapter;
import io.github.betterigo.respack.config.settings.PackPatternConfigAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.betterigo.respack.config.settings.FilterSettings;
import io.github.betterigo.respack.dec.base.DefaultRespackResultResolver;
import io.github.betterigo.respack.dec.base.RespackResultResolver;

@EnableConfigurationProperties({ FilterSettings.class })
@Configuration
public class RespackAutoConfiguration {

	@ConditionalOnMissingBean(RespackResultResolver.class)
	@Bean
	public RespackResultResolver createRespackResultResolver() {
		return new DefaultRespackResultResolver();
	}


	@ConditionalOnMissingBean(PackPatternConfigAdapter.class)
	@Bean
	public PackPatternConfigAdapter createDefaultPackPatternAdapter(){
		return new DefaultPackPatternConfigAdapter();
	}
}
