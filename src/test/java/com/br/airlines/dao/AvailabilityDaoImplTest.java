package com.br.airlines.dao;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.br.airlines.model.Availability;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class AvailabilityDaoImplTest {
	
	 @Rule
	 public WireMockRule wireMockRule = new WireMockRule(8090);
	 
	 @Autowired
	 public RestTemplate restTemplate;
	 private ResponseEntity response;
	 String body ;
	 
	 public void setupStub() {
		 body = "{  \n" +
					"   \"availability\":[  \n" +
					"            {  \n" +
					"         \"flight\":{  \n" +
					"            \"operator\":\"EI\",\n" +
					"            \"flightNumber\":\"EI554\",\n" +
					"            \"departsFrom\":\"IST\",\n" +
					"            \"arrivesAt\":\"DUB\",\n" +
					"            \"departsOn\":{  \n" +
					"               \"date\":\"02-01-2014\",\n" +
					"               \"time\":\"08:48AM\"\n" +
					"            },\n" +
					"            \"arrivesOn\":{  \n" +
					"               \"date\":\"02-01-2014\",\n" +
					"               \"time\":\"11:04AM\"\n" +
					"            },\n" +
					"            \"flightTime\":\"2:16\",\n" +
					"            \"farePrices\":{  \n" +
					"               \"first\":{  \n" +
					"                  \"ticket\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"272.00\"\n" +
					"                  },\n" +
					"                  \"bookingFee\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"17.00\"\n" +
					"                  },\n" +
					"                  \"tax\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"13.60\"\n" +
					"                  }\n" +
					"               },\n" +
					"               \"business\":{  \n" +
					"                  \"ticket\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"136.00\"\n" +
					"                  },\n" +
					"                  \"bookingFee\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"17.00\"\n" +
					"                  },\n" +
					"                  \"tax\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"13.60\"\n" +
					"                  }\n" +
					"               },\n" +
					"               \"economy\":{  \n" +
					"                  \"ticket\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"68.00\"\n" +
					"                  },\n" +
					"                  \"bookingFee\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"17.00\"\n" +
					"                  },\n" +
					"                  \"tax\":{  \n" +
					"                     \"currency\":\"EUR\",\n" +
					"                     \"amount\":\"13.60\"\n" +
					"                  }\n" +
					"               }\n" +
					"            }\n" +
					"         }\n" +
					"      }\n" +
					"   ]\n" +
					"}";
		
 
		    stubFor(get(urlEqualTo("/flights/DUB/DEL/20151007/20151020/8"))
		            .willReturn(aResponse()
		                .withHeader("Content-Type", "application/json")
		                .withStatus(200)
		                .withBody(body)));
		    
     }
	 
	 @Test
	 public void testsuccessfulResponse() throws JAXBException{
		 setupStub();
		 RestTemplate restTemplate = new RestTemplate();
		 response = restTemplate.getForEntity("http://localhost:8090//flights/DUB/DEL/20151007/20151020/8", String.class);
		 assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		 assertEquals(response.getBody().toString(),body);
	 }	 

	 
}
