<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="Smarthr - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, management, minimal, modern, accounts, invoice, html5, responsive, CRM, Projects">
        <meta name="robots" content="noindex, nofollow">
        <title>Forgot Password </title>
		<!-- Favicon -->
      <link rel="shortcut icon" type="image/x-icon" href="assets/LOGOTrade.jpg">
		
		<!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
		
		<!-- Fontawesome CSS -->
        <link rel="stylesheet" href="css/font-awesome.min.css">
		
		<!-- Main CSS -->
        <link rel="stylesheet" href="css/style.css">
		
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="assets/js/html5shiv.min.js"></script>
			<script src="assets/js/respond.min.js"></script>
		<![endif]-->
    </head>
    <body class="account-page">
	
		<!-- Main Wrapper -->
        <div class="main-wrapper">
		
			<div class="account-content">
				
				<div class="container">
				
					<!-- Account Logo -->
					<div class="account-logo">
						
						<h2>Shopping Center</h2>
					</div>
					<!-- /Account Logo -->
					
					<div class="account-box">
						<div class="account-wrapper">
							<h3 class="account-title">Forgot Password?</h3>
							<% if ("true".equals(request.getParameter("wrongEmail"))) { %>
                            <!-- Display an alert if wrongEmail parameter is present -->
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong>Oh Snap!😕</strong> Wrong Email Address. Please enter a valid email.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        <% } %>
							
							
							<!-- Account Form -->
							<form method="post" action="./PasswordResetServlet">
								<br><br><div class="form-group">
									<label>Email Address</label>
									<input placeholder="Enter your email to get a password reset link" class="form-control"  type="text" name="email">
								</div>
								<div class="form-group text-center">
									<button class="btn btn-primary account-btn" type="submit">Reset Password</button>
								</div>
								<div class="account-footer">
									<p>Remember your password? <a href="login.jsp">Login</a></p>
								</div>
							</form>
							<!-- /Account Form -->
							
						</div>
					</div>
				</div>
			</div>
        </div>
		<!-- /Main Wrapper -->
		
		<!-- jQuery -->
        <script src="js/jquery-3.2.1.min.js"></script>
		
		<!-- Bootstrap Core JS -->
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
		
		<!-- Custom JS -->
		<script src="js/app.js"></script>
		
    </body>
</html>
