package dk.kyuff.javafx.validation.handlers;

import dk.kyuff.javafx.validation.BaseErrorHandler;
import dk.kyuff.javafx.validation.ErrorHandler;
import javafx.collections.ListChangeListener;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 21.23
 */
public class CombiningErrorHandler<T> extends BaseErrorHandler<T> {

    @SafeVarargs
    public CombiningErrorHandler(ErrorHandler<T>... handlers) {
        getErrorMessages().addListener((ListChangeListener<String>) c -> {
            for (ErrorHandler<T> handler : handlers) {
                handler.getErrorMessages().setAll(getErrorMessages());
            }
        });
    }

}
