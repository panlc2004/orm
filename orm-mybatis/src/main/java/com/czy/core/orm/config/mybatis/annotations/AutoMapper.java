package com.czy.core.orm.config.mybatis.annotations;

/**
 * Created by panlc on 2017-03-17.
 */

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoMapper {

    String value() default "default";

}
