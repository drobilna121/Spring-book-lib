package ru.poskr.spring.repositories;

import ru.poskr.spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    List<Person> findByFioAndIdNot(String fio,int id);
    Optional<Person> findByUsernameAndIdNot(String username,int id);
    Optional<Person> findByUsername(String username);
}
