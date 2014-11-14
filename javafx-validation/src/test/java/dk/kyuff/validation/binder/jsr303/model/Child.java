package dk.kyuff.validation.binder.jsr303.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: swi
 * Date: 26/10/14
 * Time: 13.24
 */
public class Child {

    @NotNull(message = "a child should have a name")
    private String name;
    @Max(value = 18, message = "too old")
    private int age;
    @NotNull(message = "a child should have a friend")
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
