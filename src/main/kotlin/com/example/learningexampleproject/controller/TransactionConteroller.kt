package com.example.learningexampleproject.controller

import com.example.learningexampleproject.model.Account
import com.example.learningexampleproject.model.Transaction
import com.example.learningexampleproject.model.TransactionMassage
import com.example.learningexampleproject.repository.AccountRepository
import com.example.learningexampleproject.repository.TransactionRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.RuntimeException

@RestController
class TransactionController(
    val accountRepository: AccountRepository,
    val transactionRepository: TransactionRepository,
    private val objectMapper : ObjectMapper = ObjectMapper()
    ) {

            @KafkaListener(topics = ["topic"], groupId = "group_id")
            fun listenGroopId(msg : String) {
                checkMassage(msg)
            }

            fun checkMassage(msg : String){

                val transactionMassage  = objectMapper.readValue(msg,TransactionMassage ::class.java)


                val type = transactionMassage.type
                val id = transactionMassage.id
                val amount = transactionMassage.amount

                if (!accountRepository.existsById(id)) {
                    println("Account not found")
                    return
                }
                if (amount <= 0) {
                    println("Amount must be positive")
                    return
                }
                if (type != "increase" && type != "decrease") {
                    println("Wrong transaction type")
                    return
                }
                if (type == "increase") {
                    increase(id, amount)
                } else {
                    decrease(id, amount)
                }
            }
            @Transactional
            fun increase(id : Int, amount : Double) {
                val acc = accountRepository.findById(id)
                val newBalance = acc.get().balance + amount
                val newAcc = Account(id, acc.get().owner, newBalance)
                accountRepository.save(newAcc)
                transactionRepository.save(Transaction(UUID.randomUUID().toString(), id, amount,"increase"))
           }
            @Transactional
            fun decrease(id :Int , amount : Double ){
                val acc = accountRepository.findById(id)
                val newBalance = acc.get().balance - amount
                if (newBalance < 0) {
                    println("Not enough money")
                    return
                }
                val newAcc = Account(id, acc.get().owner, newBalance)
                accountRepository.save(newAcc)
                throw RuntimeException()
                transactionRepository.save(Transaction(UUID.randomUUID().toString(), id, amount,"decrease"))
            }
        

}