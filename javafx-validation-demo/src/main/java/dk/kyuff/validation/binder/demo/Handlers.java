package dk.kyuff.validation.binder.demo;

import javafx.scene.Node;

import java.util.List;
import java.util.function.Consumer;

/**
 * User: swi
 * Date: 14/11/14
 * Time: 17.23
 */
public class Handlers {
    public static Consumer<List<String>> styling(Node node, String style) {
        return (messages) -> {
            if (messages.size() > 0) {
                node.getStyleClass().add(style);
            } else {
                node.getStyleClass().removeAll(style);
            }
        };
    }

    public static Consumer<List<String>> messages(Consumer<String> consumer) {
        return (messages) -> {
            String message = String.join("\n", messages);
            consumer.accept(message);
        };
    }

}
