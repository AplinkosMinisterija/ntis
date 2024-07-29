package lt.project.ntis.models.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

@Pattern(regexp = "^((\\+370)\\d{8})|^(\\d{4,5})$", message = "Telefono numeris turi atitkti formatą +370******** arba būti 4 arba 5 skaitmenų ilgio")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface PhoneValidator {

    String message() default "Telefono numeris turi atitkti formatą +370******** arba būti 4 arba 5 skaitmenų ilgio";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
