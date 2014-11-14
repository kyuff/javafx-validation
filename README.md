validation-binder
=================

Proof of Concept of using java.validation with JavaFX

Run the DemoApp to try it out.



EXAMPLE
=======

This examples uses different bindings for the validator.

        ValidationBinder<Person> validator = new BeanValidator<>(Person.class)
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

