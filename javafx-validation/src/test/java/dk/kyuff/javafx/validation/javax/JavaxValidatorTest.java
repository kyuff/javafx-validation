package dk.kyuff.javafx.validation.javax;

import dk.kyuff.javafx.validation.FXValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

public class JavaxValidatorTest {

    FXValidator<Root> validator;

    RootHandler name;
    RootHandler birthday;

    Root pojo;

    @Before
    public void setUp() throws Exception {

        name = new RootHandler();
        birthday = new RootHandler();
        pojo = new Root();
        pojo.setName("Ib");
        pojo.setBirthday(LocalDate.now());

        validator = new JavaxValidator<>(Root.class)
                .bind(name, Root::getName)
                .bind(birthday, Root::getBirthday);

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