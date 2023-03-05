package ru.poskr.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.poskr.spring.models.Person;
import ru.poskr.spring.services.PersonAuthService;
import ru.poskr.spring.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonAuthService personAuthService;
    private final PersonValidator personValidator;
    @Autowired
    public AuthController(PersonAuthService personAuthService, PersonValidator personValidator) {
        this.personAuthService = personAuthService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registrationPage(@ModelAttribute("person") @Valid Person person, BindingResult result){

        personValidator.validate(person,result);

        if(result.hasErrors()){
            return  "auth/registration";
        }
        personAuthService.save(person);

        return "auth/login";
    }
}
