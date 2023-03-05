package ru.poskr.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.poskr.spring.models.Person;
import ru.poskr.spring.repositories.PeopleRepository;
import ru.poskr.spring.security.PersonDetails;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonAuthService implements UserDetailsService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public PersonAuthService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if(person.isEmpty()) throw new UsernameNotFoundException("User not found!");
        return new PersonDetails(person.get());
    }

    public boolean uniqueUsername(Person person) {
        return peopleRepository.findByUsernameAndIdNot(person.getUsername(), person.getId()).isEmpty();
    }
    @Transactional
    public void save(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}

