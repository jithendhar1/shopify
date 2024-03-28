package com.shashi.paymentphonepay;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import com.shashi.filter.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/merchant_payment")
public class merchant_payment extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String merchantId = "WEBLABSUAT";
		String merchantKey = "b65db1d1-3fdb-4a5c-82bd-a51b1bf478ae";
		String apiUrl = "https://api-preprod.phonepe.com/apis/merchant-simulator/pg/v1/pay";
		String protocol = "http";
		String host = "localhost:8081";
		String amount = null;
		int userId = 1;
		String orderId = null;
		String callbackUrl = protocol + "://" + host + "/shopping-cart/Payresponse";

		orderId = generateOrderId();
		String am = request.getParameter("amount"); // Replace with the actual amount
		double amountDouble = Double.parseDouble(am);
		int amou = (int) Math.round(amountDouble);
		amou = amou*100;
		amount = String.valueOf(amou);
		//userId = request.getParameter("1");

		String redirectUrl = protocol + "://" + host + "/shopping-cart/Phonepe_redirect?amount=" + amount + "&userId=" + userId
				+ "&orderId=" + orderId;
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
		

		phonepayPayload = phonepayPayload.replaceAll("\\s+", "");
		String phonepayBase64Payload = Base64.getEncoder().encodeToString(phonepayPayload.toString().getBytes());
		String phonepayPayload_request = "{\"request\":\"" + phonepayBase64Payload + "\"}";

		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");

		String xVerify = phonepayBase64Payload + "/pg/v1/pay" + merchantKey;
		xVerify = DigestUtils.sha256Hex(xVerify) + "###1";

		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("X-VERIFY", xVerify);
		connection.setRequestProperty("Content-Length", String.valueOf(phonepayPayload_request.length()));
		connection.setRequestProperty("Host", "api-preprod.phonepe.com");
		connection.setDoOutput(true);

		try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
			outputStream.writeBytes(phonepayPayload_request);
			outputStream.flush();
		}

		try (BufferedReader readerr = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			StringBuilder responseee = new StringBuilder();
			String linee;

			while ((linee = readerr.readLine()) != null) {
				responseee.append(linee);
			}

			String redirectUrll = responseee.toString();

			JsonObject jsonObject = JsonParser.parseString(redirectUrll).getAsJsonObject();

			String redirect_url = jsonObject.getAsJsonObject("data").getAsJsonObject("instrumentResponse")
					.getAsJsonObject("redirectInfo").get("url").getAsString();

			response.sendRedirect(redirect_url);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}

	private static String generateOrderId() {
		// Generate a 6-digit random number
		int randomDigits = new Random().nextInt(900000) + 100000;

		// Prefix with "wl"
		return "wl" + randomDigits;
	}
}
