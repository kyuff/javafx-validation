javafx-validation
=================

Proof of Concept of using java.validation with JavaFX

Run the DemoApp to try it out.



EXAMPLE
=======

This examples uses different bindings for the validator.

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
        
    submit.disableProperty().bind(validator.isValidProperty().not());

