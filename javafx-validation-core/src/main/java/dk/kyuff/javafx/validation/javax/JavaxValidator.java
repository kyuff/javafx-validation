package dk.kyuff.javafx.validation.javax;

import dk.kyuff.javafx.validation.FXValidator;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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


    private Class<T> validatedClass;
    private T proxy;
    private Validator validator;
    private ErrorHandlerMap<T> map;

    private SimpleBooleanProperty isValid;

    public JavaxValidator(Class<T> validatedClass) {
        this.validatedClass = validatedClass;
        this.proxy = createProxy();
        this.map = new ErrorHandlerMap<>();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
        this.isValid = new SimpleBooleanProperty();
    }

    /**
     * Bind errors to a particular error handler
     *
     * @param handler the error handler that must display warnings to the user
     * @param binder  a setup consumer that is required to call the methods on the entity that have the constraints this handler must take care of
     * @return the validator itself in order to allow a fluid pattern.
     */
    public JavaxValidator<T> bind(ErrorHandler<T> handler, Consumer<T> binder) {
        map.beginRecording(handler);
        binder.accept(proxy);
        map.endRecording();
        return this;
    }

    private T createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(this.getClass().getClassLoader());
        enhancer.setSuperclass(validatedClass);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            // TODO This needs to be a bit more complex.
            // So we can have a use-case with nested pojo, ie person.address.street
            String name = methodNameToPropertyName(method.getName());
            map.addField(name);
            return methodProxy.invokeSuper(o, objects);
        });
        return (T) enhancer.create();
    }

    private String methodNameToPropertyName(String name) {
        // TODO externalize to a unitTestable class
        String ret = name;
        if (ret.length() > 3 && ret.startsWith("get")) {
            ret = ret.substring(3);
        }
        if (ret.length() > 1) {
            ret = ret.substring(0, 1).toLowerCase() + ret.substring(1);
        }
        return ret;
    }

    /**
     * Execute all handlers that have been configured.
     * Those that are bound to a field with a violation will
     * receive a non-empty violation set. The rest will get
     * an empty set.
     *
     * @param entity the entity to validate.
     */
    @Override
    public void validate(T entity) {

        Set<ConstraintViolation<T>> allViolations = validator.validate(entity);

        isValid.setValue( allViolations.size() == 0 );

        Map<ErrorHandler<T>, Set<ConstraintViolation<T>>> sortedViolations = map.sort(allViolations);
        sortedViolations.forEach((handler, violations) -> handler.handle(violations));

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
