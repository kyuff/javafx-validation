package dk.kyuff.javafx.validation.javax;

import dk.kyuff.javafx.validation.FXValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JavaxValidatorTest {

    FXValidator<Parent> validator;

    List<String> nameMessages;
    List<String> birthdayMessages;

    Parent pojo;


    @Before
    public void setUp() throws Exception {

        nameMessages = new ArrayList<>();
        birthdayMessages = new ArrayList<>();

        pojo = new Parent();
        pojo.setName("Ib");
        pojo.setBirthday(LocalDate.now());
        Child child = new Child();
        child.setName("Vera");
        child.setAge(6);
        pojo.setChild(child);

        validator = new JavaxValidator<>(Parent.class)
                .bind(nameMessages::addAll, Parent::getName)
                .bind(birthdayMessages::addAll, Parent::getBirthday)
                .bind(null, Parent::getChild);

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
    }

    @Test
    public void testValidate_Error_2() throws Exception {
        // setup
        pojo.setBirthday(null);

        // execute
        validator.validate(pojo);

        // verify
        assertEquals(0, nameMessages.size());
        assertEquals(1, birthdayMessages.size());
        assertEquals("not null", birthdayMessages.get(0));
    }
}