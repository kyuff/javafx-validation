package dk.kyuff.validation.binder.jsr303.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * User: swi
 * Date: 26/10/14
 * Time: 10.32
 */
public class Parent {

    @Size(min = 1, max = 5, message = "wrong size")
    private String name;
    @NotNull(message = "not null")
    private LocalDate birthday;
    @NotNull
    @Valid
    private Child child;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
