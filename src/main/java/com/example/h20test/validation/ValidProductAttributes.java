package com.example.h20test.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ProductAttributesValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductAttributes {

    String message() default "Product-specific attributes do not match product type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
