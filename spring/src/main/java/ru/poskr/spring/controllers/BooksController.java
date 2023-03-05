package ru.poskr.spring.controllers;

import ru.poskr.spring.models.Book;
import ru.poskr.spring.models.Person;
import ru.poskr.spring.services.BookService;
import ru.poskr.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final PeopleService peopleService;
    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }
    @GetMapping
    public String index(Model model,@RequestParam(value = "page",required = false) Integer page
                                    ,@RequestParam(value = "books_per_page",required = false) Integer books_per_page
                                    ,@RequestParam(value = "sort_by_year",required = false) boolean sort_by_year){
        boolean withPages = (page!=null && books_per_page!=null);
        List<Book> books;
        if(withPages&&sort_by_year)books = bookService.findAllSortByYear(page,books_per_page);
        else if(withPages)books = bookService.findAll(page,books_per_page);
        else if(sort_by_year)books = bookService.findAllSortByYear();
        else books = bookService.findAll();

        model.addAttribute("books",books);
        return "books/index";
    }

    @GetMapping("/search")
    public String search(Model model,@RequestParam(value = "name",required = false)String name){
        model.addAttribute("name",name);
        model.addAttribute("books",bookService.findByStartName(name));
        return "books/search";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = bookService.findOne(id);

        model.addAttribute("book", book);
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("currentReader", bookService.getCurrentPerson(book));

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model,@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "books/new";
        }

        bookService.save(book);
        return "redirect:/books";

    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id,Model model){
        model.addAttribute("book",bookService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,@ModelAttribute("book") @Valid Book book,BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.update(id,book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,@ModelAttribute("currentReader") Person currentReader){
        bookService.assign(id,currentReader);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String assign(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }
}
