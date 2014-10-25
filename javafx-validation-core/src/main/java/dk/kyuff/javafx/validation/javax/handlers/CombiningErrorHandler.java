package dk.kyuff.javafx.validation.javax.handlers;

import dk.kyuff.javafx.validation.javax.ErrorHandler;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 21.23
 */
public class CombiningErrorHandler<T> implements ErrorHandler<T> {

    private ErrorHandler<T>[] handlers;

    @SafeVarargs
    public CombiningErrorHandler(ErrorHandler<T>... handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handle(Set<ConstraintViolation<T>> constraintViolations) {
        for (ErrorHandler<T> handler : handlers) {
            handler.handle(constraintViolations);
        }
    }
}
