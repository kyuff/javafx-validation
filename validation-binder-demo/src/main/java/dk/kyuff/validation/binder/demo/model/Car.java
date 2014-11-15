package dk.kyuff.validation.binder.demo.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * User: swi
 * Date: 15/11/14
 * Time: 15.52
 */
public class Car {

    @Pattern(regexp = "^V\\d+$", message = "only V engines")
    private String engine;
    @Size(min = 1, max = 15, message = "1-15 characters")
    private String price;

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
