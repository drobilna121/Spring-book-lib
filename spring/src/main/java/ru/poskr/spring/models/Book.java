package ru.poskr.spring.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

@Component
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @NotEmpty(message = "ФИО не может быть пустым")
    @Size(max = 100, message = "Длина названия должна быть не больше 100 символов")
    @Column(name = "name")
    private  String name;
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+", message = "Имя автора должно быть формата [Фамилия Имя]")
    @Column(name = "author")
    private String author;
    @Min(value = 1,message = "Год выпуска не может быть пустым!")
    @Column(name = "year")
    private int year;
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;
    @Column(name = "date_assign")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAssign;
    public Book(){}

    public Book(int id, String name, int year, String author) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.author = author;;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if(person == null)this.dateAssign = null;
        else this.dateAssign = new Date();
        this.person = person;
    }

    public Date getDateAssign() {
        return dateAssign;
    }

    public void setDateAssign(Date dateAssign) {
        this.dateAssign = dateAssign;
    }

    public boolean reservationIsOverdue(){
        if(dateAssign==null)return false;
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dateAssign);
        c.add(Calendar.DATE, 10);

        Date andReservation = c.getTime();
                //new Date(dateAssign.getTime() + (1000 * 60 *60 *24 *10));

        if(andReservation.before(currentDate))  return true;
        return false;
    }
}
