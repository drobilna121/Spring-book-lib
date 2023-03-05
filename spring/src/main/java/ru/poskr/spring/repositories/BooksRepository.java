package ru.poskr.spring.repositories;

import ru.poskr.spring.models.Book;
import ru.poskr.spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {

    List<Book> findByPerson(Person person);
    List<Book> findByNameStartingWith(String startName);
}
