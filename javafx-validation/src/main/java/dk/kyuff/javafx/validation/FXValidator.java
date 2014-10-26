package dk.kyuff.javafx.validation;

import javafx.beans.property.ReadOnlyBooleanProperty;

import java.util.function.Consumer;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 22.16
 */
public interface FXValidator<T> {
    /**
     * Bind errors to a particular error handler
     *
     * @param handler the error handler that must display warnings to the user
     * @param binder  a setup consumer that is required to call the methods on the entity that have the constraints this handler must take care of
     * @return the validator itself in order to allow a fluid pattern.
     */
    FXValidator<T> bind(ErrorHandler<T> handler, Consumer<T> binder);

    /**
     * Execute all handlers that have been configured.
     * Those that are bound to a field with a violation will
     * receive a non-empty violation set. The rest will get
     * an empty set.
     *
     * @param entity the entity to validate.
     */
    void validate(T entity);

    /**
     * Are all validations valid
     * @return true if valid
     */
    boolean getIsValid();

    /**
     * A property that specifies if all validations are valid
     * @return property to be listened to
     */
    ReadOnlyBooleanProperty isValidProperty();
}
