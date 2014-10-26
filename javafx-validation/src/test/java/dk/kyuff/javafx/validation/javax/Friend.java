package dk.kyuff.javafx.validation.javax;

import javax.validation.constraints.Size;

/**
 * User: swi
 * Date: 26/10/14
 * Time: 21.09
 */
public class Friend {

    @Size(min = 2, max = 15)
    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
