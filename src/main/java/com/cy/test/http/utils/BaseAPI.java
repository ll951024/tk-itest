package com.cy.test.http.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;

public abstract class BaseAPI {
	private static boolean logEntityStr = true;
	private static boolean logRequestEntity = true;
	private static boolean logRequestHeaders = true;
	private static boolean logResponseHeader = false;
	private static boolean logResponseStatusLine = true;
	private String entitystr;
	protected CloseableHttpResponse httpResponse;
	protected Map<String, String> urlParams = new HashMap<String, String>();
	protected List<String> pathParams = new ArrayList<String>();
	private String host;
	private String port;
	private String path;
	protected String url;
	protected CloseableHttpClient httpClient;
	protected Map<String, String> headers = new HashMap<String, String>();
	protected HttpUriRequest request;
	private HttpClientContext context = HttpClientContext.create();

	private final String STEP_STR = "--------------------------------------------------------------------------";

	private void joinUri() {

		if (this.url == null) {
			this.url = "http://" + host.trim();

			if (!StringUtils.isEmpty(this.port)) {
				this.url += ":";
				this.url += this.port.trim();
			}

			if (this.path != null) {
				this.buildPathParams();
				this.url += this.path.trim();
			}
		}

		this.setUrlParams();
	}

	@SuppressWarnings("deprecation")
	private void setUrlParams() {
		if (this.urlParams == null) {
			return;
		}

		if (this.urlParams.size() == 0) {
			return;
		}

		if (this.url.contains("?")) {
			this.url = this.url.substring(0, this.url.indexOf("?"));
		}

		if (this.urlParams.keySet().size() > 0) {
			this.url += "?";
		}

		for (String key : this.urlParams.keySet()) {
			this.url += URLEncoder.encode(key);
			this.url += "=";
			this.url += URLEncoder.encode(this.urlParams.get(key));
			this.url += "&";
		}

		if (this.urlParams.keySet().size() >= 1) {
			this.url = this.url.substring(0, this.url.length() - 1);
		}
	}

	public void buildPathParams() {
		if (this.pathParams.size() > 0) {
			for (String pathParam : this.pathParams) {
				int index = this.path.indexOf("%s");
				if (index > 0) {
					this.path = this.path.substring(0, index) + pathParam
							+ this.path.substring(index + 2, this.path.length());
				}
			}
		}
	}

	protected void setRequestInfo() {
		for (String key : this.headers.keySet()) {
			this.request.setHeader(key, this.headers.get(key));
		}
	}

	public String sendRequest(Cookie cookie) throws IOException {
		this.createHttpClientInstance(cookie);
		try {
			this.createHttpMethodAndLogRequest();
		} catch (Exception e) {
			Reporter.log(e.getMessage(), true);
			Assert.fail(e.getMessage());
		}
		this.executeAndLogResponse();
		return this.entitystr;
	}

	public String sendRequest(Cookie cookie, Integer testCaseRunId, int action_id) throws IOException {
		return this.sendRequest(cookie);
	}

	public String sendRequestWithMultiCookie(List<Cookie> cookies) throws IOException {
		this.createHttpClientInstance(cookies);
		try {
			this.createHttpMethodAndLogRequest();
		} catch (Exception e) {
			Reporter.log(e.getMessage(), true);
			Assert.fail(e.getMessage());
			return null;
		}
		this.executeAndLogResponse();
		return this.entitystr;
	}

	public String sendRequestWithMultiCookie(List<Cookie> cookies, Integer testCaseRunId, int action_id)
			throws IOException {
		return this.sendRequestWithMultiCookie(cookies);
	}

	public void verifyResponseStatus(int statusCode, String reasonPhrase) {
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), statusCode);
		Assert.assertEquals(httpResponse.getStatusLine().getReasonPhrase().toUpperCase(), reasonPhrase);
	}

	protected void executeAndLogResponse() throws IOException {
		try {
			this.httpResponse = this.httpClient.execute(this.request, this.context);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.log(e.getMessage(), true);
			Assert.fail(e.getMessage());
		}
		this.logResponse();
		this.entitystr = this.getResponseEntityStr();
		this.logResponseEntity();
	}

	protected String getResponseEntityStr() throws IOException {
		HttpEntity entity = this.httpResponse.getEntity();
		String entitystr = EntityUtils.toString(entity);
		EntityUtils.consume(entity);
		return entitystr;
	}

	public void closeConnection() throws IOException {
		httpResponse.close();
		httpClient.close();
	}

	public void createHttpClientInstance(Cookie cookie) {
		httpClient = HttpClients.createDefault();
		this.context.setCookieStore(new BasicCookieStore());
		this.context.getCookieStore().addCookie(cookie);
	}

	public void createHttpClientInstance(List<Cookie> cookies) {
		httpClient = HttpClients.createDefault();
		this.context.setCookieStore(new BasicCookieStore());
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				this.context.getCookieStore().addCookie(cookie);
			}
		}
	}

	public Cookie getCookie() {
		if (this.context.getCookieStore().getCookies().size() > 0) {
			return this.context.getCookieStore().getCookies().get(0);
		} else {
			return null;
		}
	}

	public List<Cookie> getCookies() {
		if (this.context.getCookieStore().getCookies().size() > 0) {
			return this.context.getCookieStore().getCookies();
		} else {
			return null;
		}
	}

	public void logRequest() {
		if (BaseAPI.logRequestHeaders) {
			Reporter.log(STEP_STR, true);
			Reporter.log(this.request.getRequestLine().toString(), true);
			for (Header header : this.request.getAllHeaders()) {
				Reporter.log(header.toString(), true);
			}
		}
	}

	public <T extends HttpEntityEnclosingRequest> void logRequestWithEntity(T httpmethod)
			throws ParseException, IOException {
		this.logRequest();
		if (BaseAPI.logRequestEntity && httpmethod.getEntity() != null
				&& !httpmethod.getEntity().getContentType().getValue().contains("multipart/form-data")) {
			Reporter.log(STEP_STR, true);
			Reporter.log(EntityUtils.toString(httpmethod.getEntity()), true);
		}
	}

	public void logResponse() {
		if (BaseAPI.logResponseStatusLine) {
			Reporter.log(STEP_STR, true);
			Reporter.log(this.httpResponse.getStatusLine().toString(), true);
		}

		if (BaseAPI.logResponseHeader)
			for (Header header : this.httpResponse.getAllHeaders()) {
				Reporter.log(header.toString(), true);
			}
	}

	public void logResponseEntity() {
		if (BaseAPI.logEntityStr) {
			Reporter.log(STEP_STR, true);
			Reporter.log(this.entitystr, true);
		}
	}

	public static void setLogEntityStr(boolean logEntityStr) {
		BaseAPI.logEntityStr = logEntityStr;
	}

	public static void setLogRequestEntity(boolean logRequestEntity) {
		BaseAPI.logRequestEntity = logRequestEntity;
	}

	public static void setLogRequestHeaders(boolean logRequestHeaders) {
		BaseAPI.logRequestHeaders = logRequestHeaders;
	}

	public static void setLogResponseHeader(boolean logResponseHeader) {
		BaseAPI.logResponseHeader = logResponseHeader;
	}

	public static void setLogResponseStatusLine(boolean logResponseStatusLine) {
		BaseAPI.logResponseStatusLine = logResponseStatusLine;
	}

	public int getResponseStatusCode() {
		if (httpResponse == null) {
			return -1;
		} else {
			return httpResponse.getStatusLine().getStatusCode();
		}
	}

	public abstract void createHttpMethodAndLogRequest() throws ParseException, IOException;

	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		this.joinUri();
		return this.url;
	}

	public void setUrlParams(Map<String, String> urlParams) {
		this.urlParams = urlParams;
	}

	public Map<String, String> getUrlParams() {
		return this.urlParams;
	}

	public List<String> getPathParams() {
		return pathParams;
	}

	public void setPathParams(List<String> pathParams) {
		this.pathParams = pathParams;
	}

	public Header[] getResponseHeaders() {
		return this.httpResponse.getAllHeaders();
	}

}
