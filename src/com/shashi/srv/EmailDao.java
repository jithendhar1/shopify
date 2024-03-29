package com.shashi.srv;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.shashi.utility.DBUtil;



public class EmailDao {
	public static int getFilteredEmail(String email) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DBUtil.provideConnection();
			String query;
			if (email != null && !email.isEmpty()) {
				query = "SELECT email FROM user WHERE email='" + email + "'";
			} else {
				return 0;
			}

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return 1;
			}
		} catch (Exception e) {
			// Handle exceptions
			e.printStackTrace();
		} finally {
			// Close database resources (connection, statement, result set)
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				// Handle exceptions
				e.printStackTrace();
			}
		}

		return 0;
	}

	public static void storeOTP(String email, int otp) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date currentDate = new Date();
		 String formattedDateTime = dateTimeFormat.format(currentDate);

		try {
			connection = DBUtil.provideConnection();
			String query = "INSERT INTO otpdetails (email, time, otp) VALUES (?,?, ?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, formattedDateTime);
			preparedStatement.setInt(3, otp);

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("OTP details inserted successfully.");
			} else {
				System.out.println("Failed to insert OTP details.");
			}

		} catch (Exception e) {
			// Handle exceptions
			e.printStackTrace();
		} finally {
			// Close database resources (connection, statement, result set)
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				// Handle exceptions
				e.printStackTrace();
			}
		}
	}

	public static boolean TimeOfOTP(int otp, String email) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DBUtil.provideConnection();
			String query = null;
			if (email != null && !email.isEmpty()) {

				query = "select time from otpdetails where email= ? and otp= ?";
			}

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setInt(2, otp);
			resultSet = preparedStatement.executeQuery();
			String dbTime = null;
			if (resultSet.next()) {
				dbTime = resultSet.getString("time");
			}
			System.out.println("in EmailDao line 118 " + dbTime);
			// Get the current time
			LocalDateTime currentTime = LocalDateTime.now();

			// Define the date-time formatter
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			// Format the current time using the formatter
			String formattedTime = currentTime.format(formatter);
			LocalDateTime startTime = LocalDateTime.parse(dbTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime endTime = LocalDateTime.parse(formattedTime,
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

			// Calculate the duration between the two times
			Duration duration = Duration.between(startTime, endTime);

			// Check if the duration is less than or equal to 10 minutes (600 seconds)
			boolean isWithin10Minutes = duration.getSeconds() <= 600;

			// Print the result
			if (isWithin10Minutes) {
				System.out.println("The time difference is within 10 minutes.");
				return true;
			} else {
				System.out.println("The time difference is more than 10 minutes.");
				return false;
			}

		} catch (Exception e) {
			// Handle exceptions
			e.printStackTrace();
		} finally {
			// Close database resources (connection, statement, result set)
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				// Handle exceptions
				e.printStackTrace();
			}
		}

		return false;
	}

}
