package dk.kyuff.javafx.validation;

import javafx.scene.control.Label;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 14.40
 */
public class LabelErrorHandler<T> implements ErrorHandler<T> {

    private Label label;

    public LabelErrorHandler(Label label) {
        this.label = label;
    }

    @Override
    public void handle(Set<ConstraintViolation<T>> constraintViolations) {
        String message = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .reduce("", (accumulated, violationMessage) -> accumulated + String.format("%s\n", violationMessage));
        System.out.println( label + " " + message);
        label.setText(message);
    }
}
