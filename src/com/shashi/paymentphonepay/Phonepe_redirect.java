package com.shashi.paymentphonepay;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.shashi.utility.DBUtil;
@WebServlet("/Phonepe_redirect")
public class Phonepe_redirect extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Handling request parameters
        String amount1 = request.getParameter("amount");
        int amount2=0;
        amount2 = Integer.parseInt(amount1) / 100;
        String amount = String.valueOf(amount2);
        String userId = request.getParameter("userId");
        String orderId = request.getParameter("orderId");

        try {
            // Create database connection
            Connection con = DBUtil.provideConnection();
            
            // Prepare SQL statement to insert payment data
            String insertQuery = "INSERT INTO payments (userid, orderid, amount) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            
            // Set values for the parameters
            ps.setString(1, userId);
            ps.setString(2, orderId);
            ps.setString(3, amount);
            
            // Execute the SQL statement
            ps.executeUpdate();
            
            // Close the PreparedStatement and database connection
            ps.close();
            con.close();
            
            // Print success message
            System.out.println("Payment data saved successfully.");
            
            //response.sendRedirect("OrderServlet");
            response.sendRedirect("OrderServlet?amount=" + amount);
            
        } catch (SQLException e) {
            // Print error message
            System.out.println("Error saving payment data: " + e.getMessage());
            
            // Redirect user to an error page
            response.sendRedirect("payment_error.jsp");
        }
    }
}
