package dk.kyuff.validation.binder.demo;

import dk.kyuff.validation.binder.ValidationBinder;
import dk.kyuff.validation.binder.jsr303.BeanValidator;
import dk.kyuff.validation.binder.Mapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 12.52
 */
public class DemoController implements Initializable {
    @FXML
    public Label firstNameLabel;
    @FXML
    public TextField firstName;
    @FXML
    public Label firstNameErrors;
    @FXML
    public Label lastNameLabel;
    @FXML
    public TextField lastName;
    @FXML
    public Label lastNameErrors;
    @FXML
    public Label phoneLabel;
    @FXML
    public TextField phone;
    @FXML
    public Label phoneErrors;
    @FXML
    public Button submit;
    @FXML
    public Label birthdayLabel;
    @FXML
    public DatePicker birthday;
    @FXML
    public Label birthdayErrors;

    private ValidationBinder<Person> validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validator = new BeanValidator<>(Person.class)
                .bind(Handlers.styling(firstName, "error"), Person::getFirstName)
                .bind(Handlers.messages(lastNameErrors::setText), pojo -> {
                    pojo.getLastName();
                    pojo.getPhone();
                })
                .bind(Handlers.styling(phone, "error")
                               .andThen(Handlers.messages(phoneErrors::setText)),
                        Person::getPhone)
                .bind(Handlers.messages(birthdayErrors::setText), Person::getBirthdayAsDate);

        submit.disableProperty().bind(validator.isValidProperty().not());

        nextPerson();

    }

    public void nextPerson() {
        Person person = createRandomPerson();
        setPerson(person);
    }

    public void setPerson(Person person) {

        firstName.textProperty().setValue(person.getFirstName());
        lastName.textProperty().setValue(person.getLastName());
        phone.textProperty().setValue(person.getPhone());
        birthday.setValue(person.getBirthday());

        new Mapper<>(person)
                .blur(firstName, firstName.textProperty(), person::setFirstName)
                .blur(lastName, lastName.textProperty(), person::setLastName)
                .change(phone.textProperty(), person::setPhone)
                .change(birthday.valueProperty(), person::setBirthday)
                .setValidator(validator);


    }

    public Person createRandomPerson() {
        Person person = new Person();
        Random random = new Random();

        int i = random.nextInt(9999);
        person.setFirstName("Hans " + i);
        person.setLastName("Hansen");
        person.setPhone("123-" + i);
        person.setBirthday(LocalDate.now());
        return person;
    }
}
