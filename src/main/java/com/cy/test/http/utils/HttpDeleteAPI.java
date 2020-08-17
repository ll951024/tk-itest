package com.cy.test.http.utils;

import org.apache.http.client.methods.HttpDelete;
import org.springframework.stereotype.Service;

@Service
public class HttpDeleteAPI extends BaseAPI {

	@Override
	public void createHttpMethodAndLogRequest() {
		this.request = new HttpDelete(this.getUrl());
		this.setRequestInfo();
		this.logRequest();
	}

}
