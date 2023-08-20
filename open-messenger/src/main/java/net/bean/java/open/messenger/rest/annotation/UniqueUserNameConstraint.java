package net.bean.java.open.messenger.rest.annotation;

import net.bean.java.open.messenger.rest.validation.UniqueUserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUserNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserNameConstraint {

    String message() default "The username already exists in the repository";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
