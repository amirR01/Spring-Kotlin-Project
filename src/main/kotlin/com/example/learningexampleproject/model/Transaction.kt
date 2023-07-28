package com.example.learningexampleproject.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "transaction")
class Transaction (
    @Id
    var id : String,
    @Field(type = FieldType.Integer , name = "accountId")
    val accountId : Int,
    @Field(type = FieldType.Double , name = "amount")
    val amount : Double,
    @Field(type = FieldType.Text , name = "type")
    val type : String,
)
