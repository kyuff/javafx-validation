package dk.kyuff.javafx.validation.handlers;

import dk.kyuff.javafx.validation.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * User: swi
 * Date: 26/10/14
 * Time: 06.46
 */
public abstract class BaseErrorHandler<T> implements ErrorHandler<T> {

    ObservableList<String> errorMessages;

    public BaseErrorHandler() {
        this.errorMessages = FXCollections.observableArrayList();
    }

    public ObservableList<String> getErrorMessages() {
        return errorMessages;
    }
}
