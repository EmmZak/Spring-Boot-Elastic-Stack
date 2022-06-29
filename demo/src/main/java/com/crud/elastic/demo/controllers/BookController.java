package com.crud.elastic.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.elastic.demo.elasticRepository.BookElasticRepository;
import com.crud.elastic.demo.model.Book;
import com.crud.elastic.demo.repository.BookRepository;

@RestController
public class BookController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookElasticRepository bookElasticRepository;
    
    @GetMapping(path = "/ping")
    public String ping() {
        return "ping ok";
    }

    // @GetMapping(path = "/")
    // public List<Book> findAll() {
    //     logger.info("find all called");
    //     return (List<Book>) bookRepository.findAll();
    // }

    @GetMapping(path = "/elastic/")
    public List<Book> findAllElastic() {
        
        return (List<Book>) bookElasticRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Book> findOne(@PathVariable Long id) {
        return bookRepository.findById(id);
    }

    @PostMapping(path = "/")
    public Book createBook(@RequestBody Book book) {
        logger.info("Book added " + book);
        return bookRepository.save(book);
    }
}
