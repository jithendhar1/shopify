package com.shashi.srv;

import java.util.Properties;


import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.*;
/*import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;*/
//import javax.mail.internet.InternetAddress;
import javax.mail.internet.*;
//import javax.mail.internet.MimeMessage;


public class OTPGenerator {
	private static final String FROM_EMAIL;
    private static final String FROM_PASSWORD;
    
static {
   // Load properties from the resource bundle
   ResourceBundle rb = ResourceBundle.getBundle("application");
   FROM_EMAIL = rb.getString("mailer.email");
   FROM_PASSWORD = rb.getString("mailer.password");
}

	public static String sendOTPEmail(String toEmail) {
		String otp = generateOTP(toEmail);
		String subject = "Password Reset OTP";
		String body = "Your OTP for password reset is: " + otp
				+ " valid for 10 minutes click here to reset password now : http://localhost:2503/Hrms/EnterOtp.jsp?otp="
				+ otp;
		sendEmail(toEmail, subject, body);
		return otp;
	}

	private static String generateOTP(String email) {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		// Get or create a session
//        HttpSession session = request.getSession(true);
//		session.setAttribute("otpCreationTime", System.currentTimeMillis());

		EmailDao.storeOTP(email, otp);
		System.out.println("email" + email + " " + "otp" + otp);
		return String.valueOf(otp);
	}

	private static void sendEmail(String toEmail, String subject, String body) {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Use TLSv1.2 protocol
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM_EMAIL));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}