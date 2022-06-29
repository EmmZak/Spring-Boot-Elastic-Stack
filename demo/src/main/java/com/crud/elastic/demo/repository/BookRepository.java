package com.crud.elastic.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.crud.elastic.demo.model.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    
}
