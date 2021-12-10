package io.github.betterigo.respack.dec.annotation;

import java.lang.annotation.*;

/**
 * 使用该注解来禁用结果包装
 * @author hdl
 * @since 2021/12/10 15:34
 */
@Target({
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithoutPack {
}
