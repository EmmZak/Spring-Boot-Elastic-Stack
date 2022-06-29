package com.crud.elastic.demo.elasticRepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.crud.elastic.demo.model.Book;

@Repository
public interface BookElasticRepository extends ElasticsearchRepository<Book, Long> {
    
}
