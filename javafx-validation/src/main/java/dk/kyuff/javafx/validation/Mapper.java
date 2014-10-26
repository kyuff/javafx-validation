package dk.kyuff.javafx.validation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import java.util.function.Consumer;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 13.00
 */
public class Mapper<T> {

    FXValidator<T> validator;
    T entity;

    public Mapper(T entity) {
        this.entity = entity;
    }

    public <O> Mapper<T> blur(Node node, ObservableValue<O> property, Consumer<O> consumer) {
        node.focusedProperty().addListener((observable, oldValue, focused) -> {
            if (!focused) {
                consumer.accept(property.getValue());
                validate();
            }
        });
        return this;
    }

    public <O> Mapper<T> change(ObservableValue<O> property, Consumer<O> consumer) {
        property.addListener((observable, oldValue, newValue) -> {
            consumer.accept(newValue);
            validate();
        });
        return this;
    }

    private void validate() {
        if (validator != null) {
            validator.validate(entity);
        }
    }

    public Mapper<T> setValidator(FXValidator<T> validator) {
        this.validator = validator;
        validate();
        return this;
    }

}