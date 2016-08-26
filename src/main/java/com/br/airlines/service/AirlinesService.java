package com.br.airlines.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.br.airlines.dao.AvailabilityDao;
import com.br.airlines.dao.AvailabilityDaoImpl;
import com.br.airlines.model.Airlines;
import com.br.airlines.model.ArrivesOn;
import com.br.airlines.model.Availability;
import com.br.airlines.model.Availability.Flight.Fares.Fare;
import com.br.airlines.model.AvailabilityResult;import com.br.airlines.model.BookingFee;
import com.br.airlines.model.Business;
import com.br.airlines.model.DepartsOn;
import com.br.airlines.model.Economy;
import com.br.airlines.model.FarePrices;
import com.br.airlines.model.First;
import com.br.airlines.model.Flight;
import com.br.airlines.model.Tax;
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

	private AvailabilityResult parseXml(Availability availability) {
		AvailabilityResult availabilityResult = new AvailabilityResult();
		availabilityResult.setFlight(getFlightList(availability));
		
		return availabilityResult;
	}

	private List<Flight> getFlightList(Availability availability) {
		List<Flight> flights = new ArrayList<>();
		availability.getFlight().forEach(f -> {
			flights.add(Flight.builder().operator(f.getCarrierCode())
					.flightNumber(f.getFlightDesignator())
					.departsFrom(f.getOriginAirport())
					.arrivesAt(f.getDestinationAirport())
					.departsOn(DepartsOn.builder().date(getDate(f.getDepartureDate())).time(getTime(f.getDepartureDate()).toString()).build())
					.arrivesOn(ArrivesOn.builder().date(getDate(f.getArrivalDate()).toString()).time(getTime(f.getArrivalDate()).toString()).build())
					.flightTime(calculateTime(f.getDepartureDate(),f.getArrivalDate()))
					.farePrices(FarePrices.builder().first(getFirst(f)).business(getBusiness(f)).economy(getEconomy(f)).build())					
					.build());
				
				
			});
		return flights;
	}	

	private First getFirst(com.br.airlines.model.Availability.Flight flight) {
		First first = new First();
		flight.getFares().getFare().forEach(f -> {
			if(f.getClazz().equals(FIRSTCLASS)){
				first.setTicket(Ticket.builder().currency(getCurrency(f.getBasePrice())).amount(getAmount(f.getBasePrice())).build());
				first.setTax(Tax.builder().currency(getCurrency(f.getTax())).amount(getAmount(f.getTax())).build());
				first.setBookingFee(BookingFee.builder().currency(getCurrency(f.getFees())).amount(getAmount(f.getFees())).build());
			}
		});
		
	    return first;
	}
	
	private Business getBusiness(com.br.airlines.model.Availability.Flight flight) {
		Business business = new Business();
		flight.getFares().getFare().forEach(f -> {
			if(f.getClazz().equals(BUSINESSCLASS)){
				business.setTicket(Ticket.builder().currency(getCurrency(f.getBasePrice())).amount(getAmount(f.getBasePrice())).build());
				business.setTax(Tax.builder().currency(getCurrency(f.getTax())).amount(getAmount(f.getTax())).build());
				business.setBookingFee(BookingFee.builder().currency(getCurrency(f.getFees())).amount(getAmount(f.getFees())).build());
			}
		});
		
	    return business;
	}
	
	private Economy getEconomy(com.br.airlines.model.Availability.Flight flight) {
		Economy economy = new Economy();
		flight.getFares().getFare().forEach(f -> {
			if(f.getClazz().equals(ECONOMYCLASS)){
				economy.setTicket(Ticket.builder().currency(getCurrency(f.getBasePrice())).amount(getAmount(f.getBasePrice())).build());
				economy.setTax(Tax.builder().currency(getCurrency(f.getTax())).amount(getAmount(f.getTax())).build());
				economy.setBookingFee(BookingFee.builder().currency(getCurrency(f.getFees())).amount(getAmount(f.getFees())).build());
			}
		});
		
	    return economy;
	}
	
	private String getAmount(String price) {
		return price.substring(4);
	}

	private String getCurrency(String price) {
		return price.toString().substring(0,3);
	}	
	
	private String calculateTime(XMLGregorianCalendar departureDate,XMLGregorianCalendar arrivalDate) {
			LocalTime departureTime = getTime(departureDate);
			LocalTime arriveTime = getTime(arrivalDate);
		
		return parseTime(ChronoUnit.MINUTES.between(departureTime,arriveTime));
	}
    private String parseTime(long between) {
    	String minutes = Long.toString(between % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (between / 60) + ":" + minutes;
	}

	private String getDate(XMLGregorianCalendar departureDate) {
		Date utilDate = departureDate.toGregorianCalendar().getTime();
		String localDate = LocalDateTime.ofInstant( utilDate.toInstant(), ZoneId.of("America/Sao_Paulo")).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		return localDate;
	}
	
	private LocalTime getTime(XMLGregorianCalendar departureDate) {
		DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern("hh:mm a").toFormatter();
		Date utilDate = departureDate.toGregorianCalendar().getTime();
		LocalTime localDate = LocalDateTime.ofInstant( utilDate.toInstant(), ZoneId.of("America/Sao_Paulo")).toLocalTime();
		
		return localDate;
	}	
}
