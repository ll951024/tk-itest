package com.cy.test.http.utils;

import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Service;

@Service
public class HttpGetAPI extends BaseAPI {
	
	@Override
	public void createHttpMethodAndLogRequest(){
		this.request = new HttpGet(this.getUrl());
		this.setRequestInfo();
		this.logRequest();
	}
	
}
