package com.example.learningexampleproject.model
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table()
class Account(
    @Id
    var id: Int,
    @Column
    val owner: String,
    @Column
    val balance: Double
)

