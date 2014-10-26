package dk.kyuff.javafx.validation.handlers;

import dk.kyuff.javafx.validation.BaseErrorHandler;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 20.56
 */
public class StylingErrorHandler<T> extends BaseErrorHandler<T> {

    public StylingErrorHandler(Node node, String style) {
        getErrorMessages().addListener((ListChangeListener<String>) c -> {
            if (getErrorMessages().size() > 0) {
                node.getStyleClass().add(style);
            } else {
                node.getStyleClass().removeAll(style);
            }
        });
    }

}
