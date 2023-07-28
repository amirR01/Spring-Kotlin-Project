package com.example.learningexampleproject.controller

import com.example.learningexampleproject.model.Account
import com.example.learningexampleproject.model.Amount
import com.example.learningexampleproject.model.TransactionMassage
import com.example.learningexampleproject.repository.AccountRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.handler.codec.http.HttpMethod.POST

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/account")
class AccountController(
    private val repository: AccountRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    @Value("\${LE.kafkaTransactionTopicName}")
    private val kafkaTransactionTopicName: String,
    private val objectMapper : ObjectMapper = ObjectMapper()
) {

    fun sendMessage(msg: String) {
        kafkaTemplate.send(kafkaTransactionTopicName, msg)
    }

    // get all accounts
    @GetMapping("/all")
    fun getAllAccounts(): List<Account> {
        val accounts = repository.findAll()
        return accounts.toList()
    }

    // get balance of account with id
    @GetMapping("/balance/{id}")
    fun getBalance(@PathVariable id: Int): Double {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("Account not found")
        }
        val acc = repository.findById(id)
        return acc.get().balance
    }

    // create new account
    @PostMapping("")
    fun createAccount(@RequestBody account: Account): String {
        if (repository.existsById(account.id)) {
            throw IllegalArgumentException("Account already exist")
        }
        repository.save(account)
        return "Account created"
    }

    // delete account with id
    @DeleteMapping("/{id}")
    fun deleteAccount(@PathVariable id: Int): String {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("Account not found")
        }
        repository.deleteById(id)
        return "Account deleted"
    }

    // increase balance of account with id
    @PutMapping("/increase/{id}")
    fun increase(@RequestBody increaseAmount: Amount, @PathVariable id: Int) {
        val transaction = TransactionMassage(id, increaseAmount.amount, "increase")
        // create json from transaction
        val json = objectMapper.writeValueAsString(transaction)
        // use kafka to send message to transaction service
        sendMessage(json)
    }

    // decrease balance of account with id
    @PutMapping("/decrease/{id}")
    fun decrease(@RequestBody decreaseAmount: Amount, @PathVariable id: Int) {
        val transaction = TransactionMassage(id, decreaseAmount.amount, "decrease")
        // create json from transaction
        val json = objectMapper.writeValueAsString(transaction)
        // use kafka to send message to transaction service
        sendMessage(json)
    }
}