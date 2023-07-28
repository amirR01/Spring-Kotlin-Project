package com.example.learningexampleproject.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
@EnableKafka
@Configuration
class KafkaConsumerConfig(
    @Value("\${LE.kafkaHostAndPort}")
    private val kafkaHostAndPort : String
) {
    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory(consumerConfigs())
    }

    @Bean
    fun consumerConfigs(): Map<String, Any> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaHostAndPort,
//            ConsumerConfig.GROUP_ID_CONFIG to "group_id",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer :: class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer :: class.java
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String,String> {

        var factory  : ConcurrentKafkaListenerContainerFactory<String,String> =
             ConcurrentKafkaListenerContainerFactory<String,String>()
        factory.consumerFactory = consumerFactory()
        return factory

    }

}