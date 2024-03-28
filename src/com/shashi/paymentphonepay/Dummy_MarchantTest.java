package com.shashi.paymentphonepay;

import java.io.BufferedReader;
//import org.apache.commons.lang3.StringEscapeUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;

@WebServlet("/Dummy_MarchantTest")
public class Dummy_MarchantTest extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String merchantId = "WEBLABSUAT";
		String merchantKey = "b65db1d1-3fdb-4a5c-82bd-a51b1bf478ae";

		String redirectUrl = "https://webhook.site/redirect-url";

		String callbackUrl = "https://webhook.site/callback-url";

		// Assuming the amount is being set somewhere
		String am = request.getParameter("totalAmount"); // Replace with the actual amount

		double amountDouble = Double.parseDouble(am);

		// Convert to integer by rounding
		int amou = (int) Math.round(amountDouble);
		String amount = String.valueOf(amou);
		// Transaction details
		String orderId = generateOrderId();

		String currency = "INR";
		String userId = request.getParameter("customerID"); // Replace with the actual user ID

		Map<String, Object> payloadMap = new LinkedHashMap<>();
		payloadMap.put("merchantId", merchantId);
		payloadMap.put("merchantTransactionId", orderId);
		payloadMap.put("merchantUserId", userId);
		payloadMap.put("amount", amount);
		payloadMap.put("redirectUrl", redirectUrl);
		payloadMap.put("redirectMode", "REDIRECT");
		payloadMap.put("callbackUrl", callbackUrl);
		payloadMap.put("mobileNumber", "9701999033");
		Map<String, Object> paymentInstrument = new HashMap<>();
		paymentInstrument.put("type", "PAY_PAGE");
		payloadMap.put("paymentInstrument", paymentInstrument);

		String phonepayPayload = new Gson().toJson(payloadMap);

		// Split the payload into lines
		String[] lines = phonepayPayload.split(",\\s*");

		// Manually add a space after "paymentInstrument":
		phonepayPayload = phonepayPayload.replace("\"paymentInstrument\":{", "\"paymentInstrument\": {\n  ");

		// Create a StringBuilder to build the formatted payload
		StringBuilder formattedPayload = new StringBuilder();

		for (String line : lines) {
			// Add space after the colon (:) in each line
			formattedPayload.append(line.trim()).append(",\n");
		}

		String phonepayBase64Payload = Base64.getEncoder().encodeToString(formattedPayload.toString().getBytes());
		String xVerify = phonepayBase64Payload + "/pg/v1/pay" + merchantKey;
		xVerify = DigestUtils.sha256Hex(xVerify) + "###1";
		try {

			// String escapedPayload = StringEscapeUtils.escapeJava(phonepayBase64Payload);
			// String phonepayPayload_request = "{\"request\":" + new
			// Gson().toJson(phonepayBase64Payload) + "}";
			String phonepayPayload_request = "{\r\n"
					+ "    \"request\": \"eyJtZXJjaGFudElkIjoiV0VCTEFCU1VBVCIsCiJtZXJjaGFudFRyYW5zYWN0aW9uSWQiOiJ3bDk5ODU4MyIsCiJtZXJjaGFudFVzZXJJZCI6IlZpdGFsIiwKImFtb3VudCI6Ijk2MzYiLAoicmVkaXJlY3RVcmwiOiJodHRwczovL3dlYmhvb2suc2l0ZS9yZWRpcmVjdC11cmwiLAoicmVkaXJlY3RNb2RlIjoiUkVESVJFQ1QiLAoiY2FsbGJhY2tVcmwiOiJodHRwczovL3dlYmhvb2suc2l0ZS9jYWxsYmFjay11cmwiLAoibW9iaWxlTnVtYmVyIjoiOTcwMTk5OTAzMyIsCiJwYXltZW50SW5zdHJ1bWVudCI6eyJ0eXBlIjoiUEFZX1BBR0UifX0sCg==\"\r\n"
					+ "}";

			// Construct the cURL command
			/*
			 * String curlCommand = String.format("curl --request POST " +
			 * "--url https://api-preprod.phonepe.com/apis/merchant-simulator/pg/v1/pay" +
			 * "--header 'Content-Type: application/json' " + "--header 'X-VERIFY: %s' " +
			 * "--data-raw '%s'", xVerify, phonepayPayload_request);
			 */
			String curlCommand = String.format(
					"curl --request POST " + "--url https://api-preprod.phonepe.com/apis/merchant-simulator/pg/v1/pay "
							+ "--header 'Content-Type: application/json' " + "--header 'X-VERIFY: %s' "
							+ "--header 'Content-Length: %d' " + // Add Content-Length header
							"--header 'Host: localhost' " + // Replace 'your_host_here' with the actual host
							"--data-raw '%s'",
					xVerify, phonepayPayload_request.length(), phonepayPayload_request);

			Process process = new ProcessBuilder("cmd", "/c", curlCommand).start();

			// Read the output of the command
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			}

			// Wait for the command to complete
			int exitCode = process.waitFor();
			System.out.println("Exit Code: " + exitCode);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static String generateOrderId() {
		// Generate a 6-digit random number
		int randomDigits = new Random().nextInt(900000) + 100000;

		// Prefix with "wl"
		return "wl" + randomDigits;
	}
}
