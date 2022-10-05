package com.gerimedicas.gerimedica.annotation;

import java.lang.annotation.*;

/**
 * @author volkanozturk
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLogger {
}
