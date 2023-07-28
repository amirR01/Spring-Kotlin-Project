package com.example.learningexampleproject.repository
import com.example.learningexampleproject.model.Account
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : CrudRepository<Account,Int>{
}