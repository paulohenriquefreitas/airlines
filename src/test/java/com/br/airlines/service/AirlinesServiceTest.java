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
	
	private static final String EXPECTED_ECONOMY_AMOUNT = "68.00";
	private static final String EXPECTED_BUSINESS_AMOUNT = "136.00";
	private static final String EXPECTED_FIRST_AMOUNT = "272.00";
	private static final String EXPECTED_TAX = "13.60";
	private static final String EXPECTED_BOOKINGFEE = "17.00";
	private static final String EXPECTED_CURRENCY = "EUR";
	
	
	private AirlinesService arlinesService;
	
	@Before
	public void setUp() {
		arlinesService = new AirlinesService();
	}
	

	
	@Test
	public void test_airlines_parse(){
		Availability av = createAvailabilityResult();
		assertEquals("EI",arlinesService.parseXml(av).get(0).getFlight().getOperator());
		assertEquals("EI554",arlinesService.parseXml(av).get(0).getFlight().getFlightNumber());
		assertEquals("IST",arlinesService.parseXml(av).get(0).getFlight().getDepartsFrom());
		assertEquals("DUB",arlinesService.parseXml(av).get(0).getFlight().getArrivesAt());
		assertEquals("02-01-2014",arlinesService.parseXml(av).get(0).getFlight().getDepartsOn().getDate());
		assertEquals("08:48AM",arlinesService.parseXml(av).get(0).getFlight().getDepartsOn().getTime());
		assertEquals("02-01-2014",arlinesService.parseXml(av).get(0).getFlight().getArrivesOn().getDate());
		assertEquals("11:04AM",arlinesService.parseXml(av).get(0).getFlight().getArrivesOn().getTime());
		assertEquals("2:16",arlinesService.parseXml(av).get(0).getFlight().getFlightTime());
		
		assertEquals("EI",arlinesService.parseXml(av).get(1).getFlight().getOperator());
		assertEquals("EI520",arlinesService.parseXml(av).get(1).getFlight().getFlightNumber());
		assertEquals("BOS",arlinesService.parseXml(av).get(1).getFlight().getDepartsFrom());
		assertEquals("DUB",arlinesService.parseXml(av).get(1).getFlight().getArrivesAt());
		assertEquals("05-01-2014",arlinesService.parseXml(av).get(1).getFlight().getDepartsOn().getDate());
		assertEquals("09:03AM",arlinesService.parseXml(av).get(1).getFlight().getDepartsOn().getTime());
		assertEquals("05-01-2014",arlinesService.parseXml(av).get(1).getFlight().getArrivesOn().getDate());
		assertEquals("12:13PM",arlinesService.parseXml(av).get(1).getFlight().getArrivesOn().getTime());
		assertEquals("3:10",arlinesService.parseXml(av).get(1).getFlight().getFlightTime());
	}
	
	@Test
	public void test_airlines_firstclass(){
		
		Availability av = createAvailabilityResult();
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getFirst().getTicket().getCurrency());
		assertEquals(EXPECTED_FIRST_AMOUNT,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getFirst().getTicket().getAmount());
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getFirst().getBookingFee().getCurrency());
		assertEquals(EXPECTED_BOOKINGFEE,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getFirst().getBookingFee().getAmount());
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getFirst().getTax().getCurrency());
		assertEquals(EXPECTED_TAX,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getFirst().getTax().getAmount());
	}
	
	@Test
	public void test_airlines_businessclass(){
		
		Availability av = createAvailabilityResult();
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getBusiness().getTicket().getCurrency());
		assertEquals(EXPECTED_BUSINESS_AMOUNT,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getBusiness().getTicket().getAmount());
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getBusiness().getBookingFee().getCurrency());
		assertEquals(EXPECTED_BOOKINGFEE,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getBusiness().getBookingFee().getAmount());
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getBusiness().getTax().getCurrency());
		assertEquals(EXPECTED_TAX,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getBusiness().getTax().getAmount());
	}
	
	@Test
	public void test_airlines_economyclass(){
		
		Availability av = createAvailabilityResult();
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getEconomy().getTicket().getCurrency());
		assertEquals(EXPECTED_ECONOMY_AMOUNT,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getEconomy().getTicket().getAmount());
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getEconomy().getBookingFee().getCurrency());
		assertEquals(EXPECTED_BOOKINGFEE,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getEconomy().getBookingFee().getAmount());
		assertEquals(EXPECTED_CURRENCY,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getEconomy().getTax().getCurrency());
		assertEquals(EXPECTED_TAX,arlinesService.parseXml(av).get(0).getFlight().getFarePrices().getEconomy().getTax().getAmount());
	}
	
	@Test
	public void test_get_currency(){
		assertEquals("EUR",arlinesService.getCurrency("EUR 200.00"));
	}
	
	@Test
	public void test_get_amount(){
		assertEquals("200.00",arlinesService.getAmount("EUR 200.00"));
	}
	
	@Test
	public void calculate_time_test(){
		assertEquals("3:34",arlinesService.calculateTime(getDate("2016-08-30T09:06:00.000Z"), getDate("2016-08-30T12:40:00.000Z")));
	}
	
	@Test
	public void get_time_test(){
		assertEquals("01:40PM",arlinesService.getTimeToString(getDate("2016-08-30T13:40:00.000")));
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
		first.setFees("EUR 17.00");
		first.setTax("EUR 13.60");
		fare.add(first);
		Availability.Flight.Fares.Fare business = new Availability.Flight.Fares.Fare();
		business.setBasePrice("EUR 136.00");
		business.setClazz("CIF");
		business.setFees("EUR 17.00");
		business.setTax("EUR 13.60");
		fare.add(business);
		
		
		Availability.Flight.Fares.Fare economy = new Availability.Flight.Fares.Fare();
		economy.setBasePrice("EUR 68.00");
		economy.setClazz("YIF");
		economy.setFees("EUR 17.00");
		economy.setTax("EUR 13.60");
		fare.add(economy);
		
		fares.setFare(fare);
		
		flight.setFares(fares);
		
		Availability.Flight.Fares fares02 = new Availability.Flight.Fares();
		List<Availability.Flight.Fares.Fare> fare02 = new ArrayList<>();
		
		Availability.Flight flight02 = new Availability.Flight();
		flight02.setCarrierCode("EI");
		flight02.setArrivalDate(getDate("2014-01-05T14:13:00.000Z"));
		flight02.setDepartureDate(getDate("2014-01-05T011:03:00.000Z"));
		flight02.setDestinationAirport("DUB");
		flight02.setOriginAirport("BOS");
		flight02.setFlightDesignator("EI520");
		
		Availability.Flight.Fares.Fare first02 = new Availability.Flight.Fares.Fare();
		first02.setBasePrice("EUR 272.00");
		first02.setClazz("FIF");
		first02.setFees("EUR 17.00");
		first02.setTax("EUR 13.60");
		fare02.add(first02);
		Availability.Flight.Fares.Fare business02 = new Availability.Flight.Fares.Fare();
		business02.setBasePrice("EUR 136.00");
		business02.setClazz("CIF");
		business02.setFees("EUR 17.00");
		business02.setTax("EUR 13.60");
		fare02.add(business02);
		
		
		Availability.Flight.Fares.Fare economy02 = new Availability.Flight.Fares.Fare();
		economy02.setBasePrice("EUR 68.00");
		economy02.setClazz("YIF");
		economy02.setFees("EUR 17.00");
		economy02.setTax("EUR 13.60");
		fare02.add(economy02);	
		fares02.setFare(fare02);
		
		flight02.setFares(fares02);
		
		flights.add(flight);
		flights.add(flight02);
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
