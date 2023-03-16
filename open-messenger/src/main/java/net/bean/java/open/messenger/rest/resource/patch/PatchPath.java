package net.bean.java.open.messenger.rest.resource.patch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PatchPath {

    String operation() default "";

    String pathPattern() default "";

}
