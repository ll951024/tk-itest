package com.cy.test.http.utils;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPut;
import org.springframework.stereotype.Service;

@Service
public class HttpPutAPI extends BaseHttpEntityAPI {
	
	@Override
	public void createHttpMethodAndLogRequest() throws ParseException, IOException{
		HttpPut httpPut = new HttpPut(this.getUrl());
		this.setRequestInfo(httpPut);
		this.request = httpPut;
		this.logRequestWithEntity(httpPut);
	}
	
}
