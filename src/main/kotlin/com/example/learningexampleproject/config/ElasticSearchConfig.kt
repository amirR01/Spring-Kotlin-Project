package com.example.learningexampleproject.config;

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration

@Configuration
class ElasticSearchConfig(
    @Value("\${LE.elasticHostAndPort}")
    private val elasticHostAndPort : String
) : AbstractElasticsearchConfiguration() {

    @Bean
    override fun elasticsearchClient() : RestHighLevelClient {
        val  clientConfiguration : ClientConfiguration =
            ClientConfiguration
                .builder()
                .connectedTo(elasticHostAndPort)
                .build()

        return RestClients.create(clientConfiguration).rest()
}
}

