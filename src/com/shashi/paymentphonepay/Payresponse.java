package com.shashi.paymentphonepay;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.shashi.utility.DBUtil;
@WebServlet("/Payresponse")
public class Payresponse extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Your PhonePe merchant credentials
		String merchantId = "WEBLABSUAT";
		String merchantKey = "695d0547-3728-4b1c-825d-996479133615";

		HttpSession sessionn = request.getSession();
		/*
		 * (String) sessionn.getAttribute("userId");
		 */

		JSONObject jsonResponse = new JSONObject(response.toString());
		String TransID = jsonResponse.getString("transactionId");

		String amount = (String) sessionn.getAttribute("grandtotal");
		String orderIddd = (String) sessionn.getAttribute("orderId");
		String responseCodee = (String) sessionn.getAttribute("responseCode");
		String messagee = (String) sessionn.getAttribute("message");

		String receivedSignature = "your_received_signature";// don't have
		String orderId = orderIddd;
		String status = messagee;
		String transactionId = TransID;
		String Amount = amount;
		String orderCurrency = "INR";
		String responseCode = responseCodee;
		// String responseMessage = "your_response_message";//
		String referenceId = generateReferenceId();// don't have
		// String buyerPhone = "your_buyer_phone";// don't have

		// Get the callback response (simulated, replace this with actual response data)
		Map<String, String> responseData = new HashMap<>();
		responseData.put("checksum", receivedSignature);
		responseData.put("orderId", orderId);
		responseData.put("status", status);
		responseData.put("transactionId", transactionId);
		responseData.put("Amount", Amount);
		responseData.put("orderCurrency", orderCurrency);
		responseData.put("responseCode", responseCode);
		// responseData.put("responseMessage", responseMessage);
		responseData.put("referenceId", referenceId);
		// responseData.put("buyerPhone", buyerPhone);

		// Verify the signature
		String receivedSignatures = responseData.get("checksum");
		responseData.remove("checksum");

		StringBuilder signatureBuilder = new StringBuilder(receivedSignatures);
		// Sort the data and concatenate it for signature verification
		responseData.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.forEach(entry -> signatureBuilder.append("|").append(entry.getValue()));

		// Compute the signature
		try {
			String computedSignature = HmacSHA.computeHmacSha256Signature(receivedSignatures, merchantKey);

			if (receivedSignatures.equals(computedSignature)) {
				// Signature is valid, you can trust the response

				/*
				 * String orderIdd = responseData.get("orderId"); String statuss =
				 * responseData.get("status"); String transactionIdd =
				 * responseData.get("transactionId"); String amountt =
				 * responseData.get("Amount"); String currencyy =
				 * responseData.get("orderCurrency");
				 */

				// Now, process the response
				if ("SUCCESS".equals(status)) {
					// Payment was successful, update your database or perform necessary actions
					// based on the transaction details

					// Example: Update the order status in your database
					// updateOrderStatus(orderId, "completed");

					// Insert data into the database table
					String success = status;
					String code = responseData.get("responseCode");
					// String message = responseData.get("responseMessage");
					String providerReferenceId = responseData.get("referenceId");
					// String mobilenumber = responseData.get("buyerPhone");

					// Add your database connection code here
					try {
						Connection connection = DBUtil.provideConnection();

						String sql = "INSERT INTO tblpaymentsuccess (success, code, transactionId, merchantId, amount, providerReferenceId) VALUES (?, ?, ?, ?, ?, ?)";

						PreparedStatement preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, success);
						preparedStatement.setString(2, code);
						// preparedStatement.setString(3, message);
						preparedStatement.setString(4, transactionId);
						preparedStatement.setString(5, merchantId);
						preparedStatement.setString(6, Amount);
						preparedStatement.setString(7, providerReferenceId);
						// preparedStatement.setString(8, mobilenumber);

						preparedStatement.executeUpdate();

						// Close the database connection
						connection.close();
					} catch (SQLException e) {
						// Handle database connection or query error
						System.out.println("Database error: " + e.getMessage());
					}

					System.out.println("Payment successful. Thank you!");
				} else {
					// Payment failed or was canceled
					System.out.println("Payment failed or canceled. Please try again.");
				}
			} else {
				// Invalid signature, the response might be tampered with
				System.out.println("Invalid response signature. Do not trust this response.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String generateReferenceId() {
		// Generate a random alphanumeric string
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder referenceId = new StringBuilder();
		int length = 6; // Set the desired length of the reference ID

		for (int i = 0; i < length; i++) {
			int index = new Random().nextInt(characters.length());
			referenceId.append(characters.charAt(index));
		}

		return referenceId.toString();
	}

}
