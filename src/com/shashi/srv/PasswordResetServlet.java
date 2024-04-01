package com.shashi.srv;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PasswordResetServlet")
public class PasswordResetServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String toEmail = request.getParameter("email"); // Assuming the parameter name is "email"

		if (isEmailExists(toEmail)) {
			String otp = OTPGenerator.sendOTPEmail(toEmail);
			// Store the OTP in the session to verify it later
			request.getSession().setAttribute("otp", otp);
			request.getSession().setAttribute("email", toEmail);
			// Redirect to the page where the user can enter the OTP
			response.sendRedirect("EnterOtp.jsp");
			return;

		} else {
			response.sendRedirect("forgot-password.jsp?wrongEmail=true");

		}
	}

	private boolean isEmailExists(String email) {
		EmailDao dao = new EmailDao();
		int count = dao.getFilteredEmail(email);

		if (count > 0) {

			return true;
		}
		return false; // Replace with your actual implementation
	}
}
