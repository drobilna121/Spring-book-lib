package ru.poskr.spring.services;

import ru.poskr.spring.models.Book;
import ru.poskr.spring.models.Person;
import ru.poskr.spring.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAll(int page,int books_per_page){
        return booksRepository.findAll(PageRequest.of(page,books_per_page)).getContent();
    }
    public List<Book> findAllSortByYear(){
        return booksRepository.findAll(Sort.by("year"));
    }

    public List<Book> findAllSortByYear(int page,int books_per_page){
        return booksRepository.findAll(PageRequest.of(page,books_per_page,Sort.by("year"))).getContent();
    }
    public List<Book> findByStartName(String startFio){
        if(startFio ==null){
            return new ArrayList<Book>();
        }
        return booksRepository.findByNameStartingWith(startFio);
    }

    public Book findOne(int id){
        Optional<Book> foundPerson = booksRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Book> findByPerson(Person person){
        return booksRepository.findByPerson(person);
    }

    public Person getCurrentPerson(Book book){
        Person person = book.getPerson();
        if(person == null) return new Person();
        return person;
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id,Book book){
        book.setId(id);
        booksRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int id, Person person) {
        Book book = booksRepository.getOne(id);
        book.setPerson(person);
    }

    @Transactional
    public void release(int id){
        Book book = booksRepository.getOne(id);
        book.setPerson(null);
    }
}
