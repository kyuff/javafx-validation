package dk.kyuff.javafx.validation;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 13.06
 */
public interface ErrorHandler<T> {

    void handle(Set<ConstraintViolation<T>> violations);

    ObservableList<String> getErrorMessages();

}
