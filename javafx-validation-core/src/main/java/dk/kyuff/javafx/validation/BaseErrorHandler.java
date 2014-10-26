package dk.kyuff.javafx.validation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Set;

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

    @Override
    public void handle(Set set) {

    }

    public ObservableList<String> getErrorMessages() {
        return errorMessages;
    }
}
