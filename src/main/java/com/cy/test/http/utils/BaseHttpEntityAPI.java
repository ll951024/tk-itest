package com.cy.test.http.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.CharsetUtils;

import com.alibaba.fastjson.JSONObject;

public abstract class BaseHttpEntityAPI extends BaseAPI {

	protected List<NameValuePair> requestContent;
	private String requestString;
	private Object requestObject;
	private String uploadFilePath;
	private Map<String, String> textBodys = new HashMap<String, String>();

	public Map<String, String> getTextBodys() {
		return textBodys;
	}

	public void setTextBodys(Map<String, String> textBodys) {
		this.textBodys = textBodys;
	}

	public void setRequestContent(List<NameValuePair> requestContent) {
		this.requestContent = requestContent;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}

	public String getRequestString() {
		return requestString;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	protected void setRequestInfo(HttpEntityEnclosingRequestBase httpEntityRequest)
			throws UnsupportedEncodingException {
		if (this.requestContent != null) {
			httpEntityRequest.setEntity(new UrlEncodedFormEntity(this.requestContent));
		} else if (this.requestString != null) {
			StringEntity entity = new StringEntity(this.getRequestString(), "utf-8");
			httpEntityRequest.setEntity(entity);
		} else if (this.uploadFilePath != null) {
			String filePath = new String(this.uploadFilePath);
			File file = new File(filePath);
			FileBody fileBody = new FileBody(file);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create()
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("file", fileBody);

			for (String key : this.textBodys.keySet()) {
				builder.addTextBody(key, this.textBodys.get(key));
			}

			builder.setCharset(CharsetUtils.get("UTF-8"));
			HttpEntity httpEntity = builder.build();
			httpEntityRequest.setEntity(httpEntity);
		}

		for (String key : this.headers.keySet()) {
			httpEntityRequest.setHeader(key, this.headers.get(key));
		}
	}

	public Object getRequestObject() {
		return requestObject;
	}

	public void setRequestObject(Object requestObject) {
		this.requestObject = requestObject;
		this.requestString = JSONObject.toJSONString(this.requestObject);
	}

}
