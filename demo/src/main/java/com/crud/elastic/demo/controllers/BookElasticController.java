package com.crud.elastic.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.elastic.demo.elasticRepository.BookElasticRepository;
import com.crud.elastic.demo.model.Book;

@RestController
public class BookElasticController {
    
    @Autowired
    private BookElasticRepository bookElasticRepository;

    @Autowired
    private  ElasticsearchOperations elasticsearchOperations;
    

    /*
     * Elastic
     */
    @GetMapping(path = "/books/elastic/match/{text}")
    public Iterable<Book> findAllMatchs(@PathVariable(name="text", required=true) String text) {

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(
                QueryBuilders.multiMatchQuery(text)
                    .field("title")
                    .field("category")
                    .type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
                )
            .build();

        SearchHits<Book> hits = elasticsearchOperations.search(searchQuery, Book.class);
        System.out.println(hits.getSearchHits());

        List<Book> books = new ArrayList<Book>();

        hits.forEach(e -> 
            books.add(e.getContent())
        );

        return books;
    }

    @GetMapping(path = "/books/elastic/contain/{text}")
    public Iterable<Book> findAllContain(@PathVariable(name="text", required=true) String text) {

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withFilter(
                QueryBuilders.regexpQuery("title", ".*" + text + ".*")
            )
            .build();

        SearchHits<Book> hits = elasticsearchOperations.search(searchQuery, Book.class);
        System.out.println(hits.getSearchHits());

        List<Book> books = new ArrayList<Book>();

        hits.forEach(e -> 
            books.add(e.getContent())
        );

        return books;
    }
}
