package ru.poskr.spring.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Component
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @NotEmpty(message = "ФИО не может быть пустым")
    @Size(min = 5, max = 100, message = "Длина ФИО должна быть в диапазоне от 7 до 100")
    @Pattern(regexp = "([А-Я][а-я]+ ){2}[А-Я][а-я]+", message = "ФИО должен быть формата [Фамилия Имя Отчество]")
    @Column(name = "fio")
    private  String fio;
    @Min(value = 1800,message = "Год рождения может быть не меньше 1800")
    @Column(name = "yearbday")
    private int yearbday;
    @NotEmpty(message = "Имя пользователя не может быть пустым!")
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "person")
    private List<Book> books;
    public Person(){}

    public Person(int id, String fio, int yearbday) {
        this.id = id;
        this.fio = fio;
        this.yearbday = yearbday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearbday() {
        return yearbday;
    }

    public void setYearbday(int yearbday) {
        this.yearbday = yearbday;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
