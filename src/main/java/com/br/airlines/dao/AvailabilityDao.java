package com.br.airlines.dao;



import retrofit.http.GET;
import retrofit.http.Path;

import com.br.airlines.model.Availability;

public interface AvailabilityDao {
	
	
	@GET ("/flights/{origin}/{destination}/{start}/{end}/{pax}")
	Availability getAvailability(@Path(value = "origin") String origin, 
			                     @Path(value = "destination") String destination,
			                     @Path(value = "start") String start,
			                     @Path(value = "end") String end,
			                     @Path(value = "pax") String pax);
	
	
			                     

}
