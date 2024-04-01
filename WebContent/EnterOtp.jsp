<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OTP Verification</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/style.css">

    <style type="text/css">
        .form-gap {
            padding-top: 70px;
        }
    </style>
</head>
<body>
<div class="form-gap"></div>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="text-center">
                        <h3><i class="fa fa-lock fa-4x"></i></h3>
                        <h2 class="text-center">Enter OTP</h2>
                        <% if(request.getAttribute("message")!=null) { %>
                            <p class="text-danger ml-1"><%= request.getAttribute("message") %></p>
                        <% } %>
                    </div>
                    <div class="panel-body">
                        <form id="register-form" action="./ValidateOtp" role="form" autocomplete="off"
                              class="form" method="post">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope color-blue"></i></span>
                                    <input id="opt" name="otp" placeholder="Enter OTP" class="form-control" type="text" required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <input name="recover-submit" class="btn btn-lg btn-primary btn-block" value="Reset Password" type="submit">
                            </div>
                            <input type="hidden" class="hide" name="token" id="token" value="">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var countdown = 600; // 10 minutes in seconds
    var timerDisplay = document.getElementById('timer');

    function updateTimer() {
        var minutes = Math.floor(countdown / 60);
        var seconds = countdown % 60;

        timerDisplay.textContent = minutes + ':' + (seconds < 10 ? '0' : '') + seconds;

        if (countdown > 0) {
            countdown--;
            setTimeout(updateTimer, 1000); // Update every 1 second
        } else {
            // OTP has expired, you can handle this as needed
            timerDisplay.textContent = 'Expired';
        }
    }

    // Start the timer immediately
    // window.onload = function () {
    //     updateTimer();
    // };

    // Function to get OTP from the URL
    function getOTPFromURL() {
        var urlParams = new URLSearchParams(window.location.search);
        var otp = urlParams.get('otp');
        return otp;
    }

    // Function to set OTP in the input field
    function setOTPInInputField() {
        var otpInput = document.getElementById('opt');
        var otp = getOTPFromURL();

        if (otp) {
            otpInput.value = otp;
        }
    }

    window.onload = function () {
        setOTPInInputField();
    };
</script>
</body>
</html>
