package dk.kyuff.javafx.validation.javax.handlers;

import dk.kyuff.javafx.validation.javax.ErrorHandler;
import javafx.scene.Node;

import javax.validation.ConstraintViolation;

import java.util.Set;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 20.56
 */
public class StylingErrorHandler<T> implements ErrorHandler<T> {

    private final String style;
    private final Node node;

    public StylingErrorHandler(Node node, String style) {
        this.node = node;
        this.style = style;
    }

    @Override
    public void handle(Set<ConstraintViolation<T>> constraintViolations) {
        if (constraintViolations.size() > 0) {
            node.getStyleClass().add(style);
        } else {
            node.getStyleClass().removeAll(style);
        }
    }
}
