package com.br.airlines.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.br.airlines.dao.AvailabilityDaoImpl;
import com.br.airlines.model.Availability;
import com.br.airlines.model.Availability.Flight;
import com.br.airlines.model.AvailabilityResult;
import com.br.airlines.model.Fare;

@RunWith(MockitoJUnitRunner.class)
public class AirlinesServiceTest { 
	
	private static final String ORIGIN = "DUB";
	private static final String DESTINATION = "DLE";
	private static final String START = "20151007";
	private static final String END = "20151020";
	private static final String PAX = "2";
	
	private AirlinesService arlinesService;
	
	@Before
	public void setUp() {
		arlinesService = new AirlinesService();
	}
	
	/*@Test
	public void test_airlines_parse() throws JAXBException{
		
		assertEquals(ar,arlinesService.getFlight(ORIGIN, DESTINATION, START, END, PAX));
	}*/
	
	@Test
	public void test_airlnes_parse(){
		Availability av = createAvailabilityResult();
		assertEquals("EI",arlinesService.parseXml(av).getFlight().get(0).getOperator());
		assertEquals("EI554",arlinesService.parseXml(av).getFlight().get(0).getFlightNumber());
		assertEquals("IST",arlinesService.parseXml(av).getFlight().get(0).getDepartsFrom());
		assertEquals("DUB",arlinesService.parseXml(av).getFlight().get(0).getArrivesAt());
		assertEquals("02-01-2014",arlinesService.parseXml(av).getFlight().get(0).getDepartsOn().getDate());
		assertEquals("08:48",arlinesService.parseXml(av).getFlight().get(0).getDepartsOn().getTime());
		assertEquals("02-01-2014",arlinesService.parseXml(av).getFlight().get(0).getArrivesOn().getDate());
		assertEquals("11:04",arlinesService.parseXml(av).getFlight().get(0).getArrivesOn().getTime());
		assertEquals("2:16",arlinesService.parseXml(av).getFlight().get(0).getFlightTime());
		
	}

	private Availability createAvailabilityResult() {
		List<Availability.Flight> flights = new ArrayList<>();
		Availability.Flight flight = new Availability.Flight();
		flight.setCarrierCode("EI");
		flight.setArrivalDate(getDate("2014-01-02T13:04:00.000Z"));
		flight.setDepartureDate(getDate("2014-01-02T10:48:00.000Z"));
		flight.setDestinationAirport("DUB");
		flight.setOriginAirport("IST");
		flight.setFlightDesignator("EI554");
		
		Availability.Flight.Fares fares = new Availability.Flight.Fares();
		List<Availability.Flight.Fares.Fare> fare = new ArrayList<>();
		
		Availability.Flight.Fares.Fare first = new Availability.Flight.Fares.Fare();
		first.setBasePrice("EUR 272.00");
		first.setClazz("FIF");
		first.setFees("17.00");
		first.setTax("13.60");
		fare.add(first);
		Availability.Flight.Fares.Fare business = new Availability.Flight.Fares.Fare();
		business.setBasePrice("EUR 136.00");
		business.setClazz("CIF");
		business.setFees("17.00");
		business.setTax("13.60");
		fare.add(business);
		fares.setFare(fare);
		
		Availability.Flight.Fares.Fare economy = new Availability.Flight.Fares.Fare();
		economy.setBasePrice("EUR 68.00");
		economy.setClazz("FIF");
		economy.setFees("17.00");
		economy.setTax("13.60");
		fare.add(economy);		
		
		flight.setFares(fares);
		flights.add(flight);
		Availability availability = new Availability();
		availability.setFlight(flights);
		return availability;
	}

	private XMLGregorianCalendar getDate(String date) {
		XMLGregorianCalendar cal = null;
		try {
			cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return cal;
	}

}
