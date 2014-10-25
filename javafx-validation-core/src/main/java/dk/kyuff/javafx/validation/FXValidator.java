package dk.kyuff.javafx.validation;

import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 22.16
 */
public interface FXValidator<T> {
    void validate(T entity);

    boolean getIsValid();

    ReadOnlyBooleanProperty isValidProperty();
}
