package com.cy.test.http.utils;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPatch;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HttpPatchAPI extends BaseHttpEntityAPI {
	
	@Override
	public void createHttpMethodAndLogRequest() throws ParseException, IOException{
		HttpPatch httpPatch = new HttpPatch(this.getUrl());
		this.setRequestInfo(httpPatch);
		this.request = httpPatch;
		this.logRequestWithEntity(httpPatch);
	}
	
}
