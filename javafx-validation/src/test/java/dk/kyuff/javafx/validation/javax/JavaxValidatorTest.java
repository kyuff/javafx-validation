package dk.kyuff.javafx.validation.javax;

import dk.kyuff.javafx.validation.FXValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

public class JavaxValidatorTest {

    FXValidator<Parent> validator;

    RootHandler name;
    RootHandler birthday;

    Parent pojo;


    @Before
    public void setUp() throws Exception {

        name = new RootHandler();
        birthday = new RootHandler();
        pojo = new Parent();
        pojo.setName("Ib");
        pojo.setBirthday(LocalDate.now());
        Child child = new Child();
        child.setName("Vera");
        child.setAge(6);
        pojo.setChild(child);

        validator = new JavaxValidator<>(Parent.class)
                .bind(name, Parent::getName)
                .bind(birthday, Parent::getBirthday)
                .bind(null, Parent::getChild);

    }

    @Test
    public void testValidate() throws Exception {
        // setup

        // execute
        validator.validate(pojo);

        // verify
        assertEquals(0, name.getErrorMessages().size());
        assertEquals(0, birthday.getErrorMessages().size());
    }


    @Test
    public void testValidate_Error_1() throws Exception {
        // setup
        pojo.setName("way too long for this field");

        // execute
        validator.validate(pojo);

        // verify
        assertEquals(1, name.getErrorMessages().size());
        assertEquals("wrong size", name.getErrorMessages().get(0));
        assertEquals(0, birthday.getErrorMessages().size());
    }

    @Test
    public void testValidate_Error_2() throws Exception {
        // setup
        pojo.setBirthday(null);

        // execute
        validator.validate(pojo);

        // verify
        assertEquals(0, name.getErrorMessages().size());
        assertEquals(1, birthday.getErrorMessages().size());
        assertEquals("not null", birthday.getErrorMessages().get(0));
    }
}