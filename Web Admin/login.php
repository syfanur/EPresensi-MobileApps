<!Doctype html>
<html>
<head>
  <title>Admin Area</title>
  <link rel="stylesheet" href="css/bootstrap-337.min.css">
    <link rel="stylesheet" href="font-awsome/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/login.css">
  <!--Main Stylesheet -->
  <link rel="stylesheet" href="style.css" />
</head>
<body >

<div class="container"><!-- container begin -->
       <form action="" class="form-login" method="post"><!-- form-login begin -->
           <h2 class="form-login-heading"> Admin Login </h2>

           <input type="text" class="form-control" id="u_email" placeholder="Email Address" name="admin_email" required>

           <input type="password" class="form-control" id="u_password"  placeholder="Your Password" name="admin_pass" required>

           <button type="button" name="admin_login" class="btn btn-lg btn-primary btn-block" onclick="loginUser();">Login</button>

           <!-- btn btn-lg btn-primary btn-block finish -->

       </form><!-- form-login finish -->
   </div><!-- container finish -->


<script src="https://www.gstatic.com/firebasejs/7.15.4/firebase.js"></script>

<script src="https://www.gstatic.com/firebasejs/7.15.4/firebase-analytics.js"></script>

<script>
  var firebaseConfig = {
    apiKey: "AIzaSyC017-7jws4cZBxi_XqsRtvHRYwvXwy5sQ",
    authDomain: "adhibeton-epresensi.firebaseapp.com",
    databaseURL: "https://adhibeton-epresensi.firebaseio.com",
    projectId: "adhibeton-epresensi",
    storageBucket: "adhibeton-epresensi.appspot.com",
    messagingSenderId: "649200670938",
    appId: "1:649200670938:web:4d3a84f00e72a4fd542de3",
    measurementId: "G-DYX7QV0F92"
  };

  firebase.initializeApp(firebaseConfig);
</script>
<script src="js/loginfile.js" type="text/javascript"></script>
  </body>
</html>
