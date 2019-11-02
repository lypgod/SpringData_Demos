package com.lypgod.demo.springdataproject.util.config;

import com.lypgod.demo.springdataproject.util.converter.DateToString;
import com.lypgod.demo.springdataproject.util.converter.StringToDate;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableElasticsearchRepositories(
        basePackages = "com.lypgod.demo.springdataproject.model.repository"
)
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    @Resource
    private RestClientProperties restClientProperties;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(restClientProperties.getUris().toArray(new String[0]))
                .build();

        return RestClients.create(clientConfiguration).rest();

//        return RestClients.create(ClientConfiguration.create(restClientProperties.getUris().get(0))).rest();
    }

    // no special bean creation needed

    /**
     * use the ElasticsearchEntityMapper
     */
    @Bean
    @Override
    public EntityMapper entityMapper() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new DateToString());
        converterList.add(new StringToDate());

        ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(elasticsearchMappingContext(),
                new DefaultConversionService());
        entityMapper.setConversions(new ElasticsearchCustomConversions(converterList));

        return entityMapper;
    }

}