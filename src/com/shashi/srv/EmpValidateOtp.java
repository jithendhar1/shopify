package com.shashi.srv;

import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/ValidateOtp")

public class EmpValidateOtp extends HttpServlet{

	private static final Map<String, OTPDetails> otpMap = new HashMap<>();
	private static final long serialVersionUID = 1L;

	private static class OTPDetails {
		private String otp;
		private long creationTime;
		private boolean used;

		public OTPDetails(String otp, long creationTime) {
			this.otp = otp;
			this.creationTime = creationTime;
			this.used = false;
		}

		public String getOtp() {
			return otp;
		}

		public long getCreationTime() {
			return creationTime;
		}

		public boolean isUsed() {
			return used;
		}

		public void markAsUsed() {
			this.used = true;
		}
	}

//    otpMap.put(toEmail, new OTPDetails(otp, System.currentTimeMillis()));
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int enteredOTP = Integer.parseInt(request.getParameter("otp"));
		HttpSession session = request.getSession();
		// Storing the OTP in the session
		// Rest of your code
		int storedOTP = Integer.parseInt((String) session.getAttribute("otp"));
		System.out.println("in empvalidateotp line 59");
		RequestDispatcher dispatcher = null;
		if (isWithin10Minutes(session) && enteredOTP == storedOTP) {
			/* String email=request.getParameter("email"); */
			request.setAttribute("email", request.getParameter("email"));
			request.setAttribute("status", "success");
			System.out.println("in empvalidateotp line 65");
			dispatcher = request.getRequestDispatcher("reset_password.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("message", "wrong otp");
			dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
			dispatcher.forward(request, response);
		}
	}

	// Method to check if the elapsed time is within 10 minutes
	private static boolean isWithin10Minutes(HttpSession session) {
		String email = (String) session.getAttribute("email");
		System.out.println("in EmpValidateOTP line 76 email= " + email);
		int otp = Integer.parseInt((String) session.getAttribute("otp"));
		boolean flag = EmailDao.TimeOfOTP(otp, email);
		return flag;
	}

}
