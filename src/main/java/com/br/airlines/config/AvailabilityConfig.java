package com.br.airlines.config;


import okhttp3.OkHttpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.client.Client;

import com.br.airlines.dao.AvailabilityDao;
import com.br.airlines.dao.AvailabilityDaoImpl;
import com.jakewharton.retrofit.Ok3Client;
import com.mobprofs.retrofit.converters.SimpleXmlConverter;


@Configuration
public class AvailabilityConfig {
	
	@Value("${airlines.url}")
	private String url;
	
	@Bean
	public AvailabilityDao availabilityDao(){
		return new RestAdapter.Builder().setEndpoint(url)
				.setConverter(new SimpleXmlConverter())
				.setClient(new Ok3Client(new OkHttpClient())).build().create(AvailabilityDao.class);
				
	}
	
	@Bean
	public AvailabilityDaoImpl availabilityDaoImpl(){
		return new AvailabilityDaoImpl();
	}

	 
}
