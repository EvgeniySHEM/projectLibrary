package ru.sanctio.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sanctio.spring.dao.PersonDAO;
import ru.sanctio.spring.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //посмотреть есть ли человек с таким же email в БД
        if(personDAO.getPersonByFullName(person.getFio()).isPresent()) {
            errors.rejectValue("fio", "", "Человек с таким ФИО уже существует ");
        }

    }
}
