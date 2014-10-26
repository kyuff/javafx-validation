package dk.kyuff.javafx.validation.javax;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * User: swi
 * Date: 26/10/14
 * Time: 21.09
 */
public class Friend {

    @Size(min = 2, max = 15)
    int age;

    @NotNull
    private String name;

    @Past
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
