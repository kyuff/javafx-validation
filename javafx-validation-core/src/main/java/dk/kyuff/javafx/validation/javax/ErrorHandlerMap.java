package dk.kyuff.javafx.validation.javax;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 20.25
 */
public class ErrorHandlerMap<T> {


    private ErrorHandler<T> activeHandler;

    private Map<String, List<ErrorHandler<T>>> map;

    public ErrorHandlerMap() {
        this.map = new HashMap<>();
    }

    public void beginRecording(ErrorHandler<T> handler) {
        activeHandler = handler;
    }

    public void endRecording() {
        activeHandler = null;
    }

    public void addField(String name) {
        if (activeHandler == null) {
            throw new IllegalStateException("activeHandler should not be null during a recording");
        }
        if (!map.containsKey(name)) {
            map.put(name, new ArrayList<>());

        }
        map.get(name).add(activeHandler);
    }

    public Map<ErrorHandler<T>, Set<ConstraintViolation<T>>> sort(final Set<ConstraintViolation<T>> violations) {
        Map<ErrorHandler<T>, Set<ConstraintViolation<T>>> output = new HashMap<>();

        // pre-fill the output to ensure all error handlers get a violation set - even if empty
        map.forEach((field, handlerList) -> handlerList.forEach(handler -> output.put(handler, new HashSet<>())));

        violations.forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            map.get(field).forEach(handler -> output.get(handler).add(violation));
        });

        return output;

    }
}
