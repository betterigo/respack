package io.github.betterigo.respack.config;

import io.github.betterigo.respack.config.settings.DefaultPackPatternAdapter;
import io.github.betterigo.respack.config.settings.PackPatternAdapter;
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


	@ConditionalOnMissingBean(PackPatternAdapter.class)
	@Bean
	public PackPatternAdapter createDefaultPackPatternAdapter(){
		return new DefaultPackPatternAdapter();
	}
}
