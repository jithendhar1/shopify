<%@ page import="java.util.*, javax.mail.*, javax.mail.internet.*, java.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<%@ page errorPage="" %>

<!DOCTYPE html>
<html lang="en">

<head>
<title> Reset password</title>
    <!-- Stylesheets -->
    <link rel	="shortcut icon" type="image/x-icon" href="assets/logo.png">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- Fontawesome CSS -->
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <!-- Main CSS -->
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
    <div class="main-wrapper">
	
		
		<!-- Page Wrapper -->
		<div class="page-wrapper">
    <!-- Pricing Section -->
    <section class="pricing-section spad set-bg" data-setbg="img/gym3.jpg">
        <!-- Centered Box with Border -->
    <div class="container border p-4 bg">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-sm-12">
                <!-- Pricing Section -->
                <section class="pricing-section spad set-bg" data-setbg="img/gym3.jpg">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="pricing-item entermediate">
                                    <div class="pi-top">
                                        <h2>Welcome to Reset Password</h2><br />
                                    </div>
                                    <form class="singup-form contact-form" method="GET" action="./ResetPasswordServlet">
                                        <div class="form-group">
                                            <label for="email">Enter email</label>
                                            <input type="text" class="form-control" name="email"
                                                placeholder="Enter new password" required>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label>New Password</label>
                                            <input type="password" class="form-control" name="newpassword"
                                                placeholder="Enter new password">
                                        </div>
                                        <div class="form-group">
                                            <label>Confirm Password</label>
                                            <input type="password" class="form-control" name="confirmpassword"
                                                placeholder="Confirm new password">
                                        </div>
                                        <input type="hidden" name="token" value="">
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary" name="reset">Reset Password</button>
                                        </div>
                                    </form>
                                    <!-- Include your Java code to handle password reset form submission here -->
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
    </section>
</div></div>
   <!--	Js Link      --> 
<script src="js/jquery.min.js"></script> 
<!--jQuery Layer Slider --> 
<script src="js/greensock.js"></script> 
<script src="js/layerslider.transitions.js"></script> 
<script src="js/layerslider.kreaturamedia.jquery.js"></script> 
<!--jQuery Layer Slider --> 
<script src="js/popper.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/owl.carousel.min.js"></script> 
<script src="js/tmpl.js"></script> 
<script src="js/jquery.dependClass-0.1.js"></script> 
<script src="js/draggable-0.1.js"></script> 
<script src="js/jquery.slider.js"></script> 
<script src="js/wow.js"></script> 
<script src="js/custom.js"></script>
		
</body>
</html>
