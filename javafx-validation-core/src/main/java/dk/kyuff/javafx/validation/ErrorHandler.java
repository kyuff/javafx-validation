package dk.kyuff.javafx.validation;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 13.06
 */
public interface ErrorHandler<T> {

    void handle(Set<ConstraintViolation<T>> violations);

}
