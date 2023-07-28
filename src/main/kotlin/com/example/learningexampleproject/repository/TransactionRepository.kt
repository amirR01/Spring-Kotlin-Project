package com.example.learningexampleproject.repository

import com.example.learningexampleproject.model.Transaction
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : ElasticsearchRepository<Transaction,String> {
}