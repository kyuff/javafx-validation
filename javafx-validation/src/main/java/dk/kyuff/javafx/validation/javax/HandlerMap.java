package dk.kyuff.javafx.validation.javax;

import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * User: swi
 * Date: 14/11/14
 * Time: 16.22
 */
public class HandlerMap<T> {
    private final Map<String, List<Consumer<List<String>>>> map;

    public HandlerMap() {
        this.map = new ConcurrentHashMap<>();
    }

    public void add(Consumer<List<String>> handler, List<String> fields) {
        fields.forEach(name -> {
            map.computeIfAbsent(name, (key) -> new ArrayList<>());
            map.get(name).add(handler);
        });
    }


    public Map<Consumer<List<String>>, Set<ConstraintViolation<T>>> sort(Set<ConstraintViolation<T>> violations) {
        Map<Consumer<List<String>>, Set<ConstraintViolation<T>>> output = new HashMap<>();
        // pre-fill the output to ensure all error handlers get a violation set - even if empty
        map.forEach((field, handlerList) -> handlerList.forEach(handler -> output.put(handler, new HashSet<>())));

        violations.forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            map.get(field).forEach(handler -> output.get(handler).add(violation));
        });

        return output;
    }
}
