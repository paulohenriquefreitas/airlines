package com.br.airlines.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.br.airlines.dao.AvailabilityDao;
import com.br.airlines.dao.AvailabilityDaoImpl;
import com.br.airlines.model.AvailabilityResult;


@Configuration
public class AvailabilityConfig {
	
	@Value("${airlines.url}")
	private String url;	
	
	@Bean
	public AvailabilityDao availabilityDaoImpl(){
		return new AvailabilityDaoImpl();
	}
	
	@Bean
	public AvailabilityResult availabilityResult(){
		return new AvailabilityResult();
	}
	
	@Bean
    @ConfigurationProperties(prefix = "custom.rest.connection")
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() 
    {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate customRestTemplate()
    {
        return new RestTemplate(customHttpRequestFactory());
    }

	 
}
