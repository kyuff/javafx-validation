package dk.kyuff.javafx.validation.demo;

import sun.util.calendar.LocalGregorianCalendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

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
    @Size(max = 8, message = "too long")
    @Pattern(regexp = "\\d{3}-\\d{4}", message = "match xxx-xxxx")
    private String phone;
    @Past
    private Date birthday;


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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
