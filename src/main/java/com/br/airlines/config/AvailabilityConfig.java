package com.br.airlines.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.airlines.dao.AvailabilityDao;
import com.br.airlines.dao.AvailabilityDaoImpl;


@Configuration
public class AvailabilityConfig {
	
	@Value("${airlines.url}")
	private String url;	
	
	@Bean
	public AvailabilityDao availabilityDaoImpl(){
		return new AvailabilityDaoImpl();
	}

	 
}
