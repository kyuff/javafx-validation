package dk.kyuff.validation.binder.jsr303;

import dk.kyuff.validation.binder.ValidationBinder;
import dk.kyuff.validation.binder.jsr303.internal.HandlerMap;
import dk.kyuff.validation.binder.jsr303.internal.Recorder;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 13.01
 */
public class BeanValidator<T> implements ValidationBinder<T> {

    private Validator validator;
    private final HandlerMap<T> handlerMap;
    private final Recorder<T> recorder;
    private final SimpleBooleanProperty isValid;

    public BeanValidator(Class<T> validatedClass) {
        this.handlerMap = new HandlerMap<>();
        this.recorder = new Recorder<>(validatedClass);

        this.isValid = new SimpleBooleanProperty();
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public Validator getValidator() {
        if( validator == null ) {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            this.validator = validatorFactory.getValidator();
        }
        return validator;
    }

    public ValidationBinder<T> bind(Consumer<List<String>> handler, Consumer<T> binder) {
        if (handler == null || binder == null) {
            return this;
        }
        List<String> fields = recorder.record(binder);
        handlerMap.add(handler, fields);
        return this;
    }


    @Override
    public void validate(T entity) {

        Validator v = getValidator();
        Set<ConstraintViolation<T>> allViolations = v.validate(entity);
        isValid.setValue(allViolations.size() == 0);

        Map<Consumer<List<String>>, Set<ConstraintViolation<T>>> sortedViolations = handlerMap.sort(allViolations);
        sortedViolations.forEach((handler, violations) -> {
            List<String> messages = violations.parallelStream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            handler.accept(messages);
        });

    }

    @Override
    public ReadOnlyBooleanProperty isValidProperty() {
        return isValid;
    }
}