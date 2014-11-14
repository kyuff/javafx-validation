package dk.kyuff.validation.binder.jsr303.internal;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * User: swi
 * Date: 26/10/14
 * Time: 17.15
 */
public class Recorder<T> {


    private final Class<T> validatedClass;

    private static final List<Class<?>> ILLEGAL_CLASSES = Arrays.asList(Date.class);

    public Recorder(Class<T> validatedClass) {
        this.validatedClass = validatedClass;
    }

    public List<String> record(Consumer<T> binder) {
        Node<T> root = createNode(validatedClass);
        binder.accept(root.getProxy());
        return traverse(root, "");
    }

    private List<String> traverse(Node<?> node, String acc) {
        String name = acc;
        if (node.getName() != null) {
            if ("".equals(name)) {
                name = node.getName();
            } else {
                name += "." + node.getName();
            }
        }
        List<String> result = new ArrayList<>();
        if (node.getChildren().size() == 0) {
            // end clause
            result.add(name);
        } else {
            for (Node<?> child : node.getChildren()) {
                result.addAll(traverse(child, name));
            }
        }
        return result;
    }

    private <C> Node<C> createNode(Class<C> clazz) {

        Node<C> current = new Node<>();
        if (ILLEGAL_CLASSES.contains(clazz)) {
            createReal(clazz, current);
        } else {
            createProxy(clazz, current);
        }
        return current;

    }

    private <C> void createReal(Class<C> clazz, Node<C> current) {
        try {
            C o = clazz.newInstance();
            current.setProxy(o);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <C> void createProxy(Class<C> clazz, Node<C> current) {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(this.getClass().getClassLoader());
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            String name = methodNameToPropertyName(method.getName());

            Class<?> methodReturnType = method.getReturnType();
            Node<?> child;
            // Not possible on final classes
            if (Modifier.isFinal(methodReturnType.getModifiers())) {
                child = new Node<>();
            } else {
                child = createNode(methodReturnType);
            }

            child.setName(name);
            current.addChild(child);
            return child.getProxy();
        });

        Object o = enhancer.create();
        //noinspection unchecked
        current.setProxy((C) o);
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
}
