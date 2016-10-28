package com.br.airlines.dao;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.br.airlines.model.Availability;

public class AvailabilityDaoImpl implements  AvailabilityDao{
	
	private static final Log LOG = LogFactory.getLog(AvailabilityDaoImpl.class);	
	
	@Autowired
	private RestTemplate restTemplate;
	
	public  Availability get(String origin,String destination,String start,String end,String pax,String url) throws JAXBException{
		 Availability availability = null;
		 StringBuilder strBuilder = new StringBuilder(url);
			strBuilder.append("/flights/").append(origin).append("/").append(destination).append("/").append(start)
			.append("/").append(end).append("/").append(pax);
		    try
		    {		    	
		        availability = restTemplate.getForObject(strBuilder.toString(), Availability.class);
		    } catch (Exception e) {				
				LOG.error("Ocorreu uma exceção " + e);
			} 
		return availability;
	}
	

}
