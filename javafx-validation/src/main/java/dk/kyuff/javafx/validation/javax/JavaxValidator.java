package dk.kyuff.javafx.validation.javax;

import dk.kyuff.javafx.validation.ErrorHandler;
import dk.kyuff.javafx.validation.FXValidator;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.function.Consumer;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 13.01
 */
public class JavaxValidator<T> implements FXValidator<T> {

    private Validator validator;
    private ErrorHandlerMap<T> map;

    private Recorder<T> recorder;

    private SimpleBooleanProperty isValid;

    public JavaxValidator(Class<T> validatedClass) {
        this.map = new ErrorHandlerMap<>();
        this.recorder = new Recorder<>(validatedClass);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
        this.isValid = new SimpleBooleanProperty();
    }

    @Override
    public FXValidator<T> bind(ErrorHandler<T> handler, Consumer<T> binder) {
        if (handler == null || binder == null) {
            return this;
        }
        List<String> fields = recorder.record(binder);
        map.add(handler, fields);
        return this;
    }


    @Override
    public void validate(T entity) {

        Set<ConstraintViolation<T>> allViolations = validator.validate(entity);

        isValid.setValue(allViolations.size() == 0);

        Map<ErrorHandler<T>, Set<ConstraintViolation<T>>> sortedViolations = map.sort(allViolations);
        sortedViolations.forEach((handler, violations) -> {
            handler.getErrorMessages().clear();
            violations.forEach(violation -> handler.getErrorMessages().addAll(violation.getMessage()));
        });

    }

    @Override
    public boolean getIsValid() {
        return isValid.get();
    }

    @Override
    public ReadOnlyBooleanProperty isValidProperty() {
        return isValid;
    }
}
