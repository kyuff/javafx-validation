package dk.kyuff.javafx.validation.demo;

import dk.kyuff.javafx.validation.ErrorHandler;
import dk.kyuff.javafx.validation.FXValidator;
import dk.kyuff.javafx.validation.LabelErrorHandler;
import dk.kyuff.javafx.validation.Mapper;
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
    public Label titleLabel;
    @FXML
    public TextField title;
    @FXML
    public Label titleErrors;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Person person = createPerson();

        FXValidator<Person> validator = new FXValidator<>(Person.class)
                .bind(new LabelErrorHandler<>(firstNameErrors), Person::getFirstName)
                .bind(new LabelErrorHandler<>(lastNameErrors), pojo -> {
                    pojo.getLastName();
                    pojo.getTitle();
                })
                .bind(new LabelErrorHandler<>(titleErrors), Person::getTitle);

        new Mapper<Person>()
                .map(firstName, firstName.textProperty(), person::setFirstName)
                .map(lastName, lastName.textProperty(), person::setLastName)
                .map(title, title.textProperty(), person::setTitle)
                .setValidator(validator)
                .setEntity(person);


    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstName("Hans Erik");
        person.setLastName("Hansen");
        return person;
    }
}
