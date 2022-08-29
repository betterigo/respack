package io.github.betterigo.respack.config.settings;

import java.util.List;

/**
 * @author hdl
 * @since 2022/8/29 17:22
 */
public class PackPatternAdapter {

    private WhitePattern whitePattern;

    private BlackPattern blackPattern;



    public static interface PackPattern{
        List<String> getPatterns();
    }
    /**
     * 白名单
     * 采用白名单模式时，该类配置的Pattern将被包装
     */
    public static class WhitePattern implements PackPattern {

        @Override
        public List<String> getPatterns() {
            return null;
        }
    }

    /**
     * 黑名单
     * 采用黑名单模式时，该类配置的Pattern将不被包装
     */
    public static class BlackPattern implements PackPattern {
        @Override
        public List<String> getPatterns() {
            return null;
        }
    }
}
