package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class TestAPI extends TestBase {

	TestBase testBase;
	String apiURL;
	String serviceURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeTest
	public void setUp() throws IOException {
		testBase = new TestBase();
		apiURL = prop.getProperty("URL");
		serviceURL = prop.getProperty("serviceURL");
		url = apiURL + serviceURL;
	}

	@Test(priority=1)
	public void getAPITestWithoutHeader() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse= restClient.get(url);

		// a. Status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code --> " + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, " *** Status Code is incorrect *** ");

		// b. JSON string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response JSON from API --> " + responseJSON);

		// Assertion : Per_Page
		String perPageValue = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("Value of \"per_page\" :: " + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);

		// Assertion : Per_Page
		String totalValue = TestUtil.getValueByJPath(responseJSON, "/total");
		System.out.println("Value of \"total\" :: " + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		// Get the value form JSON Array
		for (int i = 0; i < 3; i++) {

			System.out.println("********** Data from reord #" + (i + 1) + "**********");
			System.out.println("Last Name: " + TestUtil.getValueByJPath(responseJSON, "/data[" + i + "]/last_name"));
			System.out.println("Id: " + TestUtil.getValueByJPath(responseJSON, "/data[" + i + "]/id"));
			System.out.println("Avatar: " + TestUtil.getValueByJPath(responseJSON, "/data[" + i + "]/avatar"));
			System.out.println("First Name: " + TestUtil.getValueByJPath(responseJSON, "/data[" + i + "]/first_name"));
			System.out.println("******************************************");
		}
		// c. All Header
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}

		System.out.println("Header Array --> " + allHeaders);
	}

	@Test(priority=2)
	public void getAPITestWithHeader() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> hashMap= new HashMap<String, String>();
		hashMap.put("Content-Type", "Application/json");
		//hashMap.put("username", "test_user");
		//hashMap.put("password", "test_password");
		
		closeableHttpResponse= restClient.get(url, hashMap);

		// a. Status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code --> " + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, " *** Status Code is incorrect *** ");

		// b. JSON string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response JSON from API --> " + responseJSON);
/*
		// Assertion : Per_Page
		String perPageValue = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("Value of \"per_page\" :: " + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);

		// Assertion : Per_Page
		String totalValue = TestUtil.getValueByJPath(responseJSON, "/total");
		System.out.println("Value of \"total\" :: " + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		// Get the value form JSON Array
		for (int i = 0; i < 3; i++) {

			System.out.println("********** Data from reord #" + (i + 1) + "**********");
			System.out.println("Last Name: " + TestUtil.getValueByJPath(responseJSON, "/data[" + i + "]/last_name"));
			System.out.println("Id: " + TestUtil.getValueByJPath(responseJSON, "/data[" + i + "]/id"));
			System.out.println("Avatar: " + TestUtil.getValueByJPath(responseJSON, "/data[" + i + "]/avatar"));
			System.out.println("First Name: " + TestUtil.getValueByJPath(responseJSON, "/data[" + i + "]/first_name"));
			System.out.println("******************************************");
		}
		
*/
		// c. All Header
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}

		System.out.println("Header Array --> " + allHeaders);
	}
}
