package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.qa.base.TestBase;

public class RestClient extends TestBase {

	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {

		// 1. GET method without header
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);

		return closeableHttpResponse;
	}

	public CloseableHttpResponse get(String url, HashMap<String, String> hashMap)
			throws ClientProtocolException, IOException {

		// 1. GET method with header
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		
		for(Map.Entry<String, String> entry : hashMap.entrySet()) {
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);

		return closeableHttpResponse;
	}
}
