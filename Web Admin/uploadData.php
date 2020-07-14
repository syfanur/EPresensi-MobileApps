<html>
<head>
    <title>upload data to firebase</title>
</head>
<body>
<center>
    <h2>npp:</h2>
    <input type="text" id="name" required="required"><br>
    <h2>password:</h2>
    <input type="password" id="gender" required="required"><br>
    <h2>posisi:</h2>
    <input type="text" id="country" required="required"><br>
    <h2>nama:</h2>
    <input type="text" id="nama" required="required"><br>

    <button type="button" onclick="insert();">Upload</button>
</center>



<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.15.5/firebase.js"></script>

<!-- TODO: Add SDKs for Firebase products that you want to use
     https://firebase.google.com/docs/web/setup#available-libraries -->
<script src="https://www.gstatic.com/firebasejs/7.15.5/firebase-analytics.js"></script>

<script>
  // Your web app's Firebase configuration
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
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
</script>
<script src="js/upload.js" type="text/javascript"></script>
</body>
</html>