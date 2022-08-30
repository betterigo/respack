package io.github.betterigo.respack.config.settings;

import org.springframework.util.CollectionUtils;

/**
 * 默认实现，采用白名单，所有的接口都会被包装，如果再配置文件有配置的话，则会采用黑名单模式，在名单里的pattern不会被包装
 * @author hdl
 * @since 2022/8/29 23:34
 */
public class DefaultPackPatternAdapter extends PackPatternAdapter{


    @Override
    protected void config(PatternConfig config) {
        config.whitePattern().addPattern("/**");
    }
}
