package dk.kyuff.javafx.validation.demo;

import dk.kyuff.javafx.validation.FXValidator;
import dk.kyuff.javafx.validation.handlers.CombiningErrorHandler;
import dk.kyuff.javafx.validation.handlers.LabelErrorHandler;
import dk.kyuff.javafx.validation.Mapper;
import dk.kyuff.javafx.validation.handlers.StylingErrorHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Person person = createPerson();

        firstName.textProperty().setValue(person.getFirstName());
        lastName.textProperty().setValue(person.getLastName());
        phone.textProperty().setValue(person.getPhone());

        FXValidator<Person> validator = new FXValidator<>(Person.class)
                .bind(new StylingErrorHandler<>(firstName, "error"), Person::getFirstName)
                .bind(new LabelErrorHandler<>(lastNameErrors), pojo -> {
                    pojo.getLastName();
                    pojo.getPhone();
                })
                .bind(new CombiningErrorHandler<>(
                        new StylingErrorHandler<>(phone, "error"),
                        new LabelErrorHandler<>(phoneErrors)
                ), Person::getPhone);

        new Mapper<>(person)
                .map(firstName, firstName.textProperty(), person::setFirstName)
                .map(lastName, lastName.textProperty(), person::setLastName)
                .map(phone, phone.textProperty(), person::setPhone)
                .setValidator(validator);

    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstName("Hans Erik");
        person.setLastName("Hansen");
        person.setPhone("123-4567");
        return person;
    }
}
