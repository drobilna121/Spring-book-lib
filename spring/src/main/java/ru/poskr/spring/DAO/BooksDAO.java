package ru.poskr.spring.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class BooksDAO {

    private final EntityManager entityManager;

    @Autowired
    public BooksDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
