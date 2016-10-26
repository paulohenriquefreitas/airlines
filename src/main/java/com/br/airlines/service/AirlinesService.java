package com.br.airlines.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.br.airlines.dao.AvailabilityDao;
import com.br.airlines.model.Airlines;
import com.br.airlines.model.ArrivesOn;
import com.br.airlines.model.Availability;
import com.br.airlines.model.AvailabilityResult;
import com.br.airlines.model.DepartsOn;
import com.br.airlines.model.Fare;
import com.br.airlines.model.FarePrices;
import com.br.airlines.model.Flight;
import com.br.airlines.model.Ticket;


@Service
public class AirlinesService {	
	private static final String FIRSTCLASS = "FIF";
	private static final String BUSINESSCLASS = "CIF";
	private static final String ECONOMYCLASS = "YIF";

	@Value("${airlines.url}")
	private String url;
	
	@Autowired
	private AvailabilityDao availabilityDao;
	
	
	
	
	
	public Airlines getFlight(String origin, String destination,String start, String end, String pax) throws JAXBException{
		
		Availability availability = availabilityDao.get(origin, destination, start, end, pax, url);
		
		return Airlines.builder().availabilityResult(parseXml(availability)).build();
	}

	public List<AvailabilityResult> parseXml(Availability availability) {
		List<AvailabilityResult> arList = new ArrayList<>();
		availability.getFlight().forEach(f -> {
			AvailabilityResult availabilityResult = new AvailabilityResult();
			availabilityResult.setFlight(getFlightList(f));			
			arList.add(availabilityResult);
		});
		
		return arList;
	}

	private Flight getFlightList(com.br.airlines.model.Availability.Flight f) {
			return Flight.builder().operator(f.getCarrierCode())
					.flightNumber(f.getFlightDesignator())
					.departsFrom(f.getOriginAirport())
					.arrivesAt(f.getDestinationAirport())
					.departsOn(DepartsOn.builder().date(getDate(f.getDepartureDate())).time(getTimeToString(f.getDepartureDate())).build())
					.arrivesOn(ArrivesOn.builder().date(getDate(f.getArrivalDate()).toString()).time(getTimeToString(f.getArrivalDate())).build())
					.flightTime(calculateTime(f.getDepartureDate(),f.getArrivalDate()))
					.farePrices(FarePrices.builder().first(getFirst(f)).business(getBusiness(f)).economy(getEconomy(f)).build())					
					.build();
				
				
	}	

	private Fare getFirst(com.br.airlines.model.Availability.Flight flight) {
		Fare first = new Fare();
		flight.getFares().getFare().forEach(f -> {
			if(f.getClazz().equals(FIRSTCLASS)){
				first.setTicket(Ticket.builder().currency(getCurrency(f.getBasePrice())).amount(getAmount(f.getBasePrice())).build());
				first.setBookingFee(Ticket.builder().currency(getCurrency(f.getFees())).amount(getAmount(f.getFees())).build());
				first.setTax(Ticket.builder().currency(getCurrency(f.getTax())).amount(getAmount(f.getTax())).build());
				
			}
		});
		
	    return first;
	}
	
	private Fare getBusiness(com.br.airlines.model.Availability.Flight flight) {
		Fare business = new Fare();
		flight.getFares().getFare().forEach(f -> {
			if(f.getClazz().equals(BUSINESSCLASS)){
				business.setTicket(Ticket.builder().currency(getCurrency(f.getBasePrice())).amount(getAmount(f.getBasePrice())).build());
				business.setBookingFee(Ticket.builder().currency(getCurrency(f.getFees())).amount(getAmount(f.getFees())).build());
				business.setTax(Ticket.builder().currency(getCurrency(f.getTax())).amount(getAmount(f.getTax())).build());
			}
		});
		
	    return business;
	}
	
	private Fare getEconomy(com.br.airlines.model.Availability.Flight flight) {
		Fare economy = new Fare();
		flight.getFares().getFare().forEach(f -> {
			if(f.getClazz().equals(ECONOMYCLASS)){
				economy.setTicket(Ticket.builder().currency(getCurrency(f.getBasePrice())).amount(getAmount(f.getBasePrice())).build());
				economy.setBookingFee(Ticket.builder().currency(getCurrency(f.getFees())).amount(getAmount(f.getFees())).build());
				economy.setTax(Ticket.builder().currency(getCurrency(f.getTax())).amount(getAmount(f.getTax())).build());
			}
		});
		
	    return economy;
	}
	
	public String getAmount(String price) {
		return price.substring(4);
	}

	public String getCurrency(String price) {
		return price.toString().substring(0,3);
	}	
	
	public String calculateTime(XMLGregorianCalendar departureDate,XMLGregorianCalendar arrivalDate) {
			LocalTime departureTime = getTime(departureDate);
			LocalTime arriveTime = getTime(arrivalDate);
		
		return parseTime(ChronoUnit.MINUTES.between(departureTime,arriveTime));
	}
    private String parseTime(long between) {
    	String minutes = Long.toString(between % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (between / 60) + ":" + minutes;
	}

	public String getDate(XMLGregorianCalendar date) {
		Date utilDate = date.toGregorianCalendar().getTime();
		String localDate = LocalDateTime.ofInstant( utilDate.toInstant(), ZoneId.of("America/Sao_Paulo")).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		return localDate;
	}
	
	public LocalTime getTime(XMLGregorianCalendar departureDate) {
		Date utilDate = departureDate.toGregorianCalendar().getTime();
		LocalTime localDate = LocalDateTime.ofInstant( utilDate.toInstant(), ZoneId.of("America/Sao_Paulo")).toLocalTime();
		
		return localDate;
	}
	
	public String getTimeToString(XMLGregorianCalendar departureDate) {
		Date utilDate = departureDate.toGregorianCalendar().getTime();
		DateFormat readFormat = new SimpleDateFormat( "hh:mmaa");
		readFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		
		return readFormat.format(utilDate);
	}	
}
