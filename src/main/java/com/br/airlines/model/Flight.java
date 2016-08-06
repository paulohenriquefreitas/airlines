package com.br.airlines.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public class Flight
{
	private String operator;
	
	private String flightNumber;
	
	private String departsFrom;
	
	private String arrivesAt;
	
	private DepartsOn departsOn;
	
	private ArrivesOn arrivesOn;
	
	private String flightTime;

    private FarePrices farePrices;

}