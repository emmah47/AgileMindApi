package com.javabean.agilemind.configurations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class SimpleMongoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate(@Value("${mongodb.url}") String mongodbConn) {
        return new MongoTemplate(MongoClients.create(mongodbConn),"AgileMind");
    }
}
