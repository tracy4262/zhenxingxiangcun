package com.ovit.nswy.wiki.config.search;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * Created by ${huipei.x} on 2017-10-17.
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchConfig {

        @Autowired
        private ElasticsearchProperties elasticsearchProperties;
       @Bean
        public TransportClient client() throws Exception {
                Settings settings = Settings.builder().put("cluster.name", elasticsearchProperties.getEsClusterName()).build();
                TransportClient client = new PreBuiltTransportClient(settings)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticsearchProperties.getEsHost()), elasticsearchProperties.getEsPort()));
                return client;
        }


}
