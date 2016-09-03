package com.br.airlines.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.br.airlines.model.Airlines;
import com.br.airlines.service.AirlinesService;

@RunWith(MockitoJUnitRunner.class)
public class AirlinesControllerTest {
	
	private static final String ORIGIN = "DUB";
	private static final String DESTINATION = "DLE";
	private static final String START = "20151007";
	private static final String END = "20151020";
	private static final String PAX = "2";
	
	@InjectMocks
	private AirlinesController airlinesController;
	
	@Mock
	private AirlinesService airlinesService;
	
	@Test
	public void test_success() throws Exception {
		when(airlinesService.getFlight(ORIGIN, DESTINATION, START, END, PAX)).thenReturn(new Airlines());
		assertEquals(OK, airlinesController.get(ORIGIN, DESTINATION, START, END, PAX).getStatusCode());
	}
	
	@Test(expected=RuntimeException.class)
	public void test_error_when_get_flight() throws Exception {
		when(airlinesService.getFlight(ORIGIN, DESTINATION, START, END, PAX)).thenThrow(new RuntimeException());
		
		airlinesController.get(ORIGIN, DESTINATION, START, END, PAX);
	}

}
