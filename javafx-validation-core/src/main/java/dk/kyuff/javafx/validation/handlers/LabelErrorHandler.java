package dk.kyuff.javafx.validation.handlers;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 14.40
 */
public class LabelErrorHandler<T> extends BaseErrorHandler<T> {

    public LabelErrorHandler(Label label) {
        getErrorMessages().addListener((ListChangeListener<String>) c -> {
            String message = getErrorMessages().stream()
                    .reduce("", (accumulated, violationMessage) -> accumulated + String.format("%s\n", violationMessage));
            label.setText(message);
        });
    }

}
