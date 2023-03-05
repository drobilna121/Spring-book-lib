package ru.poskr.spring.util;

import ru.poskr.spring.models.Person;
import ru.poskr.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.poskr.spring.services.PersonAuthService;

@Component
public class PersonValidator implements Validator {

    private  final PeopleService peopleService;
    private  final PersonAuthService personAuthService;
    @Autowired
    public PersonValidator(PeopleService peopleService, PersonAuthService personAuthService) {
        this.peopleService = peopleService;
        this.personAuthService = personAuthService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if(!peopleService.uniqueFio(person)){
            errors.rejectValue("fio", "", "Человек с таким ФИО уже существует!");
        }
        if(!personAuthService.uniqueUsername(person));
    }
}
