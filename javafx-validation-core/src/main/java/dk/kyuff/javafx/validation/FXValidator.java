package dk.kyuff.javafx.validation;

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
public class FXValidator<T> {


    private Class<T> validatedClass;
    private T proxy;
    private List<String> usedFields;
    private Map<String, List<ErrorHandler<T>>> handlers;
    private Validator validator;

    public FXValidator(Class<T> validatedClass) {
        this.validatedClass = validatedClass;
        this.handlers = new HashMap<>();
        this.proxy = createProxy();
        this.usedFields = new ArrayList<>();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    /**
     * Bind errors to a particular error handler
     *
     * @param handler the error handler that must display warnigns to the user
     * @param binder  a setup consumer that is required to call the methods on the entity that have the constraints this handler must take care of
     * @return the validator itself in order to allow a fluid pattern.
     */
    public FXValidator<T> bind(ErrorHandler<T> handler, Consumer<T> binder) {

        usedFields.clear();
        binder.accept(proxy);
        usedFields.forEach(field -> {
            if (!handlers.containsKey(field)) {
                handlers.put(field, new ArrayList<>());
            }
            handlers.get(field).add(handler);
        });
        return this;
    }

    private T createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(this.getClass().getClassLoader());
        enhancer.setSuperclass(validatedClass);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            // TODO This needs to be a bit more complex.
            // So we can have a usecase with nested pojos, ie person.address.streetname
            String name = methodNameToPropertyName(method.getName());
            usedFields.add(name);
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
     * Execute all validations that have been configured
     */
    public void validate(T entity) {

        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        Map<String, Set<ConstraintViolation<T>>> sortedViolations = sortViolationsBasedOnFieldName(violations);
        handlers.forEach((path, handlerList) -> {
            Set<ConstraintViolation<T>> violationForPath = sortedViolations.get(path);
            if (violationForPath == null) {
                // The case with no violations on a particular field
                violationForPath = new HashSet<>();
            }
            for(ErrorHandler<T> handler : handlerList) {
                handler.handle(violationForPath);
            }
        });

    }

    private Map<String, Set<ConstraintViolation<T>>> sortViolationsBasedOnFieldName(Set<ConstraintViolation<T>> violations) {
        Map<String, Set<ConstraintViolation<T>>> sortedViolations = new HashMap<>();
        violations.forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            if (!sortedViolations.containsKey(path)) {
                sortedViolations.put(path, new HashSet<>());
            }
            sortedViolations.get(path).add(violation);
        });
        return sortedViolations;
    }

}
