package com.br.airlines.dao;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.jayway.restassured.RestAssured.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Result;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class AvailabilityDaoImplTest {
	
	 @Rule
	 public static WireMockRule wireMockRule = new WireMockRule(8090);
	 
	 public void setupStub() {
         
		    stubFor(get(urlEqualTo("/flights/DUB/DEL/20151007/20151020/8"))
		            .willReturn(aResponse()
		                .withHeader("Content-Type", "text/plain")
		                .withStatus(200)
		                .withBody("You've reached a valid WireMock endpoint")));
     }
	 
	 @Test
	 public void testsuccessfulResponse(){
		 setupStub();		 
	 }
	 
	 
}
