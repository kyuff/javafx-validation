javafx-validation
=================

Proof of Concept of using java.validation with JavaFX

Run DemoApp to try it out. There are used different methods to map inputs to the pojo and to validate the data.

EXAMPLE
=======

    FXValidator<Person> validator = new JavaxValidator<>(Person.class)
        .bind(new StylingErrorHandler<>(firstName, "error"), Person::getFirstName)
        .bind(new LabelErrorHandler<>(lastNameErrors), pojo -> {
                    pojo.getLastName();
                    pojo.getPhone();
            })
        .bind(new CombiningErrorHandler<>(
                    new StylingErrorHandler<>(phone, "error"),
                    new LabelErrorHandler<>(phoneErrors)
            ), Person::getPhone)
        .bind(new LabelErrorHandler<>(birthdayErrors), Person::getBirthdayAsDate);
