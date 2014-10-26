package dk.kyuff.javafx.validation.javax;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: swi
 * Date: 26/10/14
 * Time: 13.24
 */
public class Child {

    @NotNull
    private String name;
    @Size(max = 18)
    private int age;
    @NotNull
    private Friend friend;

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

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }
}
