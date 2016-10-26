package com.br.airlines.config;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
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
	public RestTemplate restTemplate() {
	    return getRestTemplateConfig();
	}

	@SuppressWarnings("deprecation")
	private RestTemplate getRestTemplateConfig() {
		
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setMaxConnectionsPerHost(100);
        connectionManager.setMaxTotalConnections(200);
        HttpClientParams httpClientParams = new HttpClientParams();
        httpClientParams.setConnectionManagerTimeout(20000);
        httpClientParams.setSoTimeout(2000);
        
        HttpClient client = new HttpClient(httpClientParams, connectionManager);
        ClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory(client);
        RestTemplate restTemplate = new RestTemplate(factory);
        
		return restTemplate;
	}

	 
}
