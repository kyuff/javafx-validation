package dk.kyuff.validation.binder.jsr303.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * User: swi
 * Date: 26/10/14
 * Time: 19.56
 */
public class Node<R> {

    private String name;
    private R proxy;
    private final List<Node<?>> children;

    public Node() {
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProxy(R proxy) {
        this.proxy = proxy;
    }

    public R getProxy() {
        return proxy;
    }

    public void addChild(Node<?> child) {
        children.add(child);
    }

    public List<Node<?>> getChildren() {
        return children;
    }
}
