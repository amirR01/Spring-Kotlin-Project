package com.example.learningexampleproject.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
class KafkaProducerConfig(
    @Value("\${LE.kafkaHostAndPort}")
    private val kafkaHostAndPort : String
) {


    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(producerConfigs()!!)
    }

    @Bean
    fun producerConfigs(): Map<String, Any>? {
        return mapOf(
               ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaHostAndPort,
               ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
               ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
           )
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory())
    }







}