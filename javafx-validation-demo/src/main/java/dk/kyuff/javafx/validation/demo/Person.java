package dk.kyuff.javafx.validation.demo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 13.01
 */
public class Person {

    @NotNull(message = "must be set")
    @Size(min = 5, max = 10, message = "wrong size")
    private String firstName;
    @Size(min = 3, max = 7, message = "wrong size")
    private String lastName;
    @Size(max = 3, message = "too long")
    private String title;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
