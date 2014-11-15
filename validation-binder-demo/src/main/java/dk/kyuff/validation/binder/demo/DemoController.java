package dk.kyuff.validation.binder.demo;

import dk.kyuff.validation.binder.ValidationBinder;
import dk.kyuff.validation.binder.demo.model.Car;
import dk.kyuff.validation.binder.demo.model.Person;
import dk.kyuff.validation.binder.jsr303.BeanValidator;
import dk.kyuff.validation.binder.Mapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
    public TextField firstName;
    @FXML
    public Label nameErrors;
    @FXML
    public TextField lastName;
    @FXML
    public TextField phone;
    @FXML
    public Label phoneErrors;
    @FXML
    public Button submit;
    @FXML
    public DatePicker birthday;
    @FXML
    public Label birthdayErrors;
    @FXML
    public BorderPane nameBox;
    @FXML
    public Label carErrors;
    @FXML
    public TextField engine;
    @FXML
    public TextField price;

    private ValidationBinder<Person> validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validator = new BeanValidator<>(Person.class)
                // simple binding - a normal use case
                .bind(Handlers.messages(phoneErrors::setText), Person::getPhone)
                // multi binding with chained handlers
                .bind(Handlers.messages(nameErrors::setText).andThen(
                                Handlers.styling(nameBox, "error")
                        ), pojo -> {
                            pojo.getFirstName();
                            pojo.getLastName();
                        }
                )
                // binding to fields in the entity object graph
                .bind(Handlers.messages(carErrors::setText), pojo -> {
                    pojo.getCar().getEngine();
                    pojo.getCar().getPrice();
                });


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

        engine.setText(person.getCar().getEngine());
        price.setText(person.getCar().getPrice());

        new Mapper<>(person)
                .blur(firstName, firstName.textProperty(), person::setFirstName)
                .blur(lastName, lastName.textProperty(), person::setLastName)
                .change(phone.textProperty(), person::setPhone)
                .change(birthday.valueProperty(), person::setBirthday)
                .change(engine.textProperty(), person.getCar()::setEngine)
                .change(price.textProperty(), person.getCar()::setPrice)
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

        Car car = new Car();
        car.setEngine("V8");
        car.setPrice("Expensive");

        person.setCar(car);
        return person;
    }
}
