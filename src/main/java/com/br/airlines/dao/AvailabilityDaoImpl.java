package com.br.airlines.dao;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.br.airlines.model.Availability;

public class AvailabilityDaoImpl implements  AvailabilityDao{
	
	private static final Log LOG = LogFactory.getLog(AvailabilityDaoImpl.class);	
	
	public  Availability get(String origin,String destination,String start,String end,String pax,String url) throws JAXBException{
		 Availability availability = null;
		 DefaultHttpClient httpClient = new DefaultHttpClient();
		 StringBuilder strBuilder = new StringBuilder(url);
			strBuilder.append("/flights/").append(origin).append("/").append(destination).append("/").append(start)
			.append("/").append(end).append("/").append(pax);
		    try
		    {
		    	HttpGet getRequest = new HttpGet(strBuilder.toString());
		    	getRequest.addHeader("accept", "application/xml");
		    	HttpResponse response = httpClient.execute(getRequest);
		    	int statusCode = response.getStatusLine().getStatusCode();
		        if (statusCode != 200) 
		        {
		            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
		        }
		        HttpEntity httpEntity = response.getEntity();
		        String apiOutput = EntityUtils.toString(httpEntity);
		        JAXBContext jaxbContext = JAXBContext.newInstance(Availability.class);
		        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		        availability = (Availability) jaxbUnmarshaller.unmarshal(new StringReader(apiOutput)); 	
		    } catch (HttpException e) {				
				LOG.error("Ocorreu uma exceção " + e);
			} catch (IOException e) {
				LOG.error("Ocorreu uma exceção " + e);
			}finally
		    {
		        httpClient.getConnectionManager().shutdown();
		    }		
		return availability;
	}

}
