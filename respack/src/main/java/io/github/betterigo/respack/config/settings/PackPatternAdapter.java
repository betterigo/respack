package io.github.betterigo.respack.config.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.betterigo.respack.config.settings.PackPatternMode.BLACK_LIST;
import static io.github.betterigo.respack.config.settings.PackPatternMode.WHITE_LIST;


/**
 * 配置黑白名单模式以及匹配路径
 * @author hdl
 * @since 2022/8/29 17:22
 */
public abstract class PackPatternAdapter {

    private PackPattern packPattern;

    protected abstract void config(PatternConfig config);

    public String patternMode(){
        if(Objects.equals(packPattern.getClass(),WhitePattern.class)){
            return WHITE_LIST;
        }
        if(Objects.equals(packPattern.getClass(),BlackPattern.class)){
            return BLACK_LIST;
        }
        return WHITE_LIST;
    }

    public List<String> getPatterns(){
        PatternConfig patternConfig = new PatternConfig();
        config(patternConfig);
        this.packPattern = patternConfig.packPattern;
        return packPattern.getPatterns();
    }

    public static class PatternConfig{
        private PackPattern packPattern;
        public PatternConfig whitePattern(){
            this.packPattern = new WhitePattern();
            return this;
        }
        public PatternConfig blackPattern(){
            this.packPattern = new BlackPattern();
            return this;
        }
        public PatternConfig addPattern(String pattern){
            this.packPattern.addPattern(pattern);
            return this;
        }
    }

    public static interface PackPattern{
        List<String> getPatterns();
        void addPattern(String pattern);
    }

    public static class NeedPattern implements PackPattern{

        private List<String> patterns;

        public NeedPattern() {
            patterns = new ArrayList<>();
        }

        @Override
        public List<String> getPatterns() {
            return patterns;
        }

        @Override
        public void addPattern(String pattern) {
            patterns.add(pattern);
        }
    }
    /**
     * 白名单
     * 采用白名单模式时，该类配置的Pattern将被包装
     */
    public static class WhitePattern extends NeedPattern {

    }

    /**
     * 黑名单
     * 采用黑名单模式时，该类配置的Pattern将不被包装
     */
    public static class BlackPattern extends NeedPattern {
    }
}
