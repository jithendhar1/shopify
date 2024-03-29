/*
 * package com.weblabs.srv;
 * 
 * import java.io.*;
 * 
 * import java.sql.Connection; import java.sql.PreparedStatement; import
 * java.sql.SQLException; import javax.servlet.*; import
 * javax.servlet.annotation.WebServlet; import javax.servlet.http.*; import
 * com.weblabs.utility.DBUtil;
 * 
 * @WebServlet("/ResetPasswordServlet") public class ResetPasswordServlet
 * extends HttpServlet {
 * 
 * protected void doGet(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { String email =
 * request.getParameter("email"); String newPassword =
 * request.getParameter("newpassword"); String confirmPassword =
 * request.getParameter("confirmpassword"); String error = ""; String msg = "";
 * 
 * // Validate whether the password and confirm password fields match if
 * (!newPassword.equals(confirmPassword)) { error = "Passwords do not match.";
 * request.setAttribute("error", error);
 * request.getRequestDispatcher("reset_password.jsp").forward(request,
 * response); } else { String status = "Updating Goals Failed!"; Connection con
 * = DBUtil.provideConnection(); PreparedStatement ps = null; try { ps =
 * con.prepareStatement("UPDATE employees SET Password=? WHERE Email=?");
 * ps.setString(1, newPassword); ps.setString(2, email); int k =
 * ps.executeUpdate();
 * 
 * if (k > 0) { status = "Password Change successful"; } } catch (SQLException
 * e) { status = "Error: " + e.getMessage(); e.printStackTrace(); } finally {
 * DBUtil.closeConnection(con); DBUtil.closeConnection(ps); } // Assuming the
 * reset is successful msg = "Password reset successful.";
 * request.setAttribute("msg", msg);
 * request.getRequestDispatcher("login.jsp").forward(request, response); } }
 * 
 * 
 * 
 * protected void doPost(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { doGet(request, response); }
 * }
 * 
 * 
 * 
 * 
 * 
 */

package com.shashi.srv;

import java.io.IOException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shashi.utility.DBUtil;


@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");

		String newPassword = request.getParameter("newpassword");
		String confirmPassword = request.getParameter("confirmpassword");
		String error = "";
		String msg = "";
		boolean isVerified = false;
		Connection con1 = DBUtil.provideConnection();
		PreparedStatement ps1 = null;
		String status1 = "Updating Goals Failed!";
		try {
			ps1 = con1.prepareStatement("select * from user where email=? ");
			ps1.setString(1, email);
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) {
				isVerified = true;
			}
		} catch (SQLException e) {
			status1 = "Error: " + e.getMessage();
			e.printStackTrace();
		}
		if (isVerified) {
			// Validate whether the password and confirm password fields match
			if (!newPassword.equals(confirmPassword)) {
				error = "Passwords do not match.";
				request.setAttribute("error", error);
				request.getRequestDispatcher("reset_password.jsp").forward(request, response);
			} else {
				String status = "Updating Goals Failed!";
				Connection con = DBUtil.provideConnection();
				PreparedStatement ps = null;
				try {
					ps = con.prepareStatement("UPDATE user SET password=? WHERE email=?");
					ps.setString(1, newPassword);
					ps.setString(2, email);
					int k = ps.executeUpdate();
					if (k > 0) {
						status = "Password Change successful";
					}
				} catch (SQLException e) {
					status = "Error: " + e.getMessage();
					e.printStackTrace();
				} finally {
					DBUtil.closeConnection(con);
					DBUtil.closeConnection(ps);
				}
				// Assuming the reset is successful
				msg = "Password reset successful.";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} else {
			error = "oldpassword or email is wrong.";
			request.setAttribute("error", error);
			request.getRequestDispatcher("reset_password.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}