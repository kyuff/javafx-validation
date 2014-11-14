package dk.kyuff.validation.binder.jsr303;

import dk.kyuff.validation.binder.ValidationBinder;
import dk.kyuff.validation.binder.jsr303.model.Child;
import dk.kyuff.validation.binder.jsr303.model.Friend;
import dk.kyuff.validation.binder.jsr303.model.Parent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BeanValidatorTest {

    ValidationBinder<Parent> validator;

    List<String> nameMessages;
    List<String> birthdayMessages;
    List<String> childMessages;

    Parent pojo;


    @Before
    public void setUp() throws Exception {

        nameMessages = new ArrayList<>();
        birthdayMessages = new ArrayList<>();
        childMessages = new ArrayList<>();

        // Parent
        pojo = new Parent();
        pojo.setName("Ib");
        pojo.setBirthday(LocalDate.now());

        // Child
        Child child = new Child();
        child.setName("Vera");
        child.setAge(6);

        // Friend
        Friend friend = new Friend();
        friend.setAge(11);

        // Wire
        pojo.setChild(child);
        child.setFriend(friend);

        validator = new BeanValidator<>(Parent.class)
                .bind(nameMessages::addAll, Parent::getName)
                .bind(birthdayMessages::addAll, Parent::getBirthday)
                .bind(null, Parent::getChild)

                .bind(childMessages::addAll, entity -> {
                    entity.getChild().getName();
                    entity.getChild().getAge();
                });


    }

    @Test
    public void testValidate() throws Exception {
        // setup

        // execute
        validator.validate(pojo);

        // verify
        assertEquals(0, nameMessages.size());
        assertEquals(0, birthdayMessages.size());
    }


    @Test
    public void testValidate_Error_1() throws Exception {
        // setup
        pojo.setName("way too long for this field");

        // execute
        validator.validate(pojo);

        // verify
        assertEquals(1, nameMessages.size());
        assertEquals("wrong size", nameMessages.get(0));
        assertEquals(0, birthdayMessages.size());
        assertEquals(0, childMessages.size());
    }

    @Test
    public void testValidate_Error_2() {
        // setup
        pojo.setBirthday(null);

        // execute
        validator.validate(pojo);

        // verify
        assertEquals(0, nameMessages.size());
        assertEquals(1, birthdayMessages.size());
        assertEquals(0, childMessages.size());
        assertEquals("not null", birthdayMessages.get(0));
    }

    @Test
    public void testValidate_childNodes_checked() {
        // setup
        pojo.getChild().setFriend(null); // childMessages doesn't listen to friends
        pojo.getChild().setName(null);
        pojo.getChild().setAge(42);

        // execute
        validator.validate(pojo);

        // verify
        assertEquals(0, nameMessages.size());
        assertEquals(0, birthdayMessages.size());
        assertEquals(2, childMessages.size());
        assertTrue(childMessages.contains("a child should have a name"));
        assertTrue(childMessages.contains("too old"));

    }
}