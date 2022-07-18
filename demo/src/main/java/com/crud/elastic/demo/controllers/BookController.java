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

    @Autowired
    private BookElasticRepository bookElasticRepository;

    @Autowired
    private  ElasticsearchOperations elasticsearchOperations;
    
    @GetMapping(path = "/ping")
    public String ping() {
        return "ping ok";
    }

    @GetMapping(path = "/books")
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @GetMapping(path = "/books/elastic")
    public Iterable<Book> findAllElastic(@RequestParam(name="text", required=false) String text) {
        if (text == null) {
            return bookElasticRepository.findAll();
        }
        System.out.println("param is " + text);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.multiMatchQuery(text)
                .field("title")
                .field("category")
                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS))
            .build();

        NativeSearchQuery searchQuery2 = new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.multiMatchQuery(text)
                .field("title")
                .field("category")
                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS))
            .build();
        // Query q = new NativeSearchQueryBuilder()
        //     .withFilter(QueryBuilders.matchQuery("title", title))
        //     .build();

        SearchHits<Book> hits = elasticsearchOperations.search(searchQuery, Book.class);
        System.out.println(hits.getSearchHits());

        List<Book> books = new ArrayList<Book>();

        hits.forEach(e -> 
            books.add(e.getContent())
        );

        return books;
    }

    // @GetMapping(path = "/{id}")
    // public Optional<Book> findOne(@PathVariable Long id) {
    //     return bookRepository.findById(id);
    // }

    // @PostMapping(path = "/")
    // public Book createBook(@RequestBody Book book) {
    //     logger.info("Book added " + book);
    //     return bookRepository.save(book);
    // }
}
