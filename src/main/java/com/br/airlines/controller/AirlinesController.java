package com.br.airlines.controller;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.airlines.model.Airlines;
import com.br.airlines.model.AvailabilityResult;
import com.br.airlines.service.AirlinesService;

@RestController
@RequestMapping("flights")
public class AirlinesController {
	
	@Autowired
	private AirlinesService airlinesService;
	
	@RequestMapping(value = "/{origin}/{destination}/{start}/{end}/{pax}", method=RequestMethod.GET)	
	public ResponseEntity<Airlines> get(@PathVariable String origin, @PathVariable String destination,
						 			   @PathVariable String start, @PathVariable String end, @PathVariable String pax) throws JAXBException{
		
		Airlines availability = airlinesService.getFlight(origin, destination,start, end, pax);
		
		return new ResponseEntity<Airlines>(availability, HttpStatus.OK) ;		
	}

}
