package net.bean.java.open.messenger.rest.validation;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.annotation.UniqueUserNameConstraint;
import net.bean.java.open.messenger.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserNameConstraint, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.getUserByUserName(value).isPresent();
    }

    @Override
    public void initialize(UniqueUserNameConstraint constraintAnnotation) {
    }

}
