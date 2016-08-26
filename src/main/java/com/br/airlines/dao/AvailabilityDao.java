package com.br.airlines.dao;



import javax.xml.bind.JAXBException;

import retrofit.http.GET;
import retrofit.http.Path;

import com.br.airlines.model.Availability;

public interface AvailabilityDao {	

	Availability get(String origin,String destination,String start,String end,String pax,String url) throws JAXBException;

}
