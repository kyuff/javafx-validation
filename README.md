validation-binder
=================

Proof of Concept of using java.validation with JavaFX

Run the DemoApp to try it out.

Read motivation here: http://blog.kyuff.dk/2014/11/binding-to-bean-validations.html


EXAMPLE
=======

This examples uses different bindings for the validator.

        ValidationBinder<Person> validator = new BeanValidator<>(Person.class)
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


