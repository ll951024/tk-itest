package com.cy.test.http.utils;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;

@Service
public class HttpPostAPI extends BaseHttpEntityAPI {

	@Override
	public void createHttpMethodAndLogRequest() throws ParseException, IOException {
		HttpPost httpPost = new HttpPost(this.getUrl());
		httpPost.addHeader("Content-Type","application/json");
		this.setRequestInfo(httpPost);
		this.request = httpPost;
		this.logRequestWithEntity(httpPost);
	}

}
