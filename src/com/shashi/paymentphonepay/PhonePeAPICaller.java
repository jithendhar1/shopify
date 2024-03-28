package com.shashi.paymentphonepay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/PhonePeAPICaller")
public class PhonePeAPICaller extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// API endpoint URL
		String merchantId = "WEBLABSUAT";
		String merchantKey = "b65db1d1-3fdb-4a5c-82bd-a51b1bf478ae";
		String apiUrl = "https://api-preprod.phonepe.com/apis/merchant-simulator/pg/v1/pay";

		// API request body
		String requestBody = "{\"request\": \"eyJtZXJjaGFudElkIjoiV0VCTEFCU1VBVCIsIm1lcmNoYW50VHJhbnNhY3Rpb25JZCI6IndsNTkyMDY2IiwibWVyY2hhbnRVc2VySWQiOiJWaXRhbCIsImFtb3VudCI6IjIyMiIsInJlZGlyZWN0VXJsIjoiaHR0cHM6Ly93ZWJob29rLnNpdGUvcmVkaXJlY3QtdXJsIiwicmVkaXJlY3RNb2RlIjoiUkVESVJFQ1QiLCJjYWxsYmFja1VybCI6Imh0dHBzOi8vd2ViaG9vay5zaXRlL2NhbGxiYWNrLXVybCIsIm1vYmlsZU51bWJlciI6Ijk3MDE5OTkwMzMiLCJwYXltZW50SW5zdHJ1bWVudCI6eyJ0eXBlIjoiUEFZX1BBR0UifX0=\"}";

		// Set up the URL connection
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// Set the request method to POST
		connection.setRequestMethod("POST");
		String xVerify = requestBody + "/pg/v1/pay" + merchantKey;
		xVerify = DigestUtils.sha256Hex(xVerify) + "###1";

		// Set request headers
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("X-VERIFY",
				"c8ee3bb4d32fc72ee4ef031a0f9a27e3a74dfdfa187f9836194e7eacde82f954###1");
		connection.setRequestProperty("Content-Length", String.valueOf(requestBody.length()));
		connection.setRequestProperty("Host", "api-preprod.phonepe.com");

		// Enable input/output streams for writing and reading data
		connection.setDoOutput(true);

		// Write the request body to the output stream
		try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
			outputStream.writeBytes(requestBody);
			outputStream.flush();
		}

		try (BufferedReader readerr = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			StringBuilder responseee = new StringBuilder();
			String linee;

			while ((linee = readerr.readLine()) != null) {
				responseee.append(linee);
			}

			// Print the response
			System.out.println("API Response: " + responseee.toString());

			// Assuming responsee.toString() contains the URL you want to redirect to
			String redirectUrl = responseee.toString();

			// Parse the JSON string into a JsonObject
			JsonObject jsonObject = JsonParser.parseString(redirectUrl).getAsJsonObject();

			// Extract the URL
			String redirect_url = jsonObject.getAsJsonObject("data").getAsJsonObject("instrumentResponse")
					.getAsJsonObject("redirectInfo").get("url").getAsString();
			// Extract the URL
			/*
			 * String redirect_url =
			 * jsonObject.getJSONObject("data").getJSONObject("instrumentResponse")
			 * .getJSONObject("redirectInfo").getString("url");
			 */

			System.out.println("Extracted URL: " + redirect_url);

			// Redirect to the URL
			response.sendRedirect(redirect_url);
		} catch (IOException e) {
			// Handle IOException (e.g., log the exception)
			e.printStackTrace();
		} finally {
			// Close the connection
			connection.disconnect();
		}
	}
}
