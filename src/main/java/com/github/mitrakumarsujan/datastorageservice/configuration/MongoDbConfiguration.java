package com.github.mitrakumarsujan.datastorageservice.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MongoDbConfigurationProperties.class)
public class MongoDbConfiguration {

    @Autowired
    private MongoDbConfigurationProperties properties;

    @Bean
    public MongoDatabase getMongoDatabase(MongoClient client) {
        return client.getDatabase(properties.getDatabase());
    }
}
