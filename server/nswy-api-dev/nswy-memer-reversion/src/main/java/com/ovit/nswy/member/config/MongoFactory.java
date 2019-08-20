package com.ovit.nswy.member.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MongoFactory配置类
 **/
@Configuration
public class MongoFactory {

    private static final Logger logger = LogManager.getLogger(MongoFactory.class);

    public static final String DEFAULT_URI = "mongodb://localhost/test";

    private String database;

    @Value("${nswy.mongodb.uri}")
    private String uri;

    private String determineUri() {
        return (this.uri != null ? this.uri : DEFAULT_URI);
    }

    @Bean(name = "mongoClient", destroyMethod = "close")
    public MongoClient MongoClient(@Qualifier("mongoOptions") MongoOptions mongoOptions) {
        MongoClientURI mongoClientURI = new MongoClientURI(determineUri(), mongoOptions.getOptionsBuilder());
        this.database = mongoClientURI.getDatabase();
        logger.info("initial mongoClient...");
        return new MongoClient(mongoClientURI);
    }

    @Bean(name = "mongoDatabase")
    public MongoDatabase getMongoDatabase(@Qualifier("mongoClient") MongoClient mongoClient) {
        return mongoClient.getDatabase(this.database);
    }
}
