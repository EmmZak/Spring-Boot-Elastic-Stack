package com.crud.elastic.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.elastic.demo.elasticRepository.BookElasticRepository;
import com.crud.elastic.demo.model.Book;
import com.crud.elastic.demo.repository.BookRepository;

@RestController
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping(path = "/books")
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @PostMapping(path = "/books")
    public Book create(@RequestBody(required = true) Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping(path = "/books/{id}")
    public void delete(@PathVariable(required = true) Long id ) {
        bookRepository.deleteById(id);
    }
}
