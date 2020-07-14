<html>
<head>
    <title>upload image to firebase</title>
</head>
<body>
<center>
    <form enctype="multipart/form-data">
        <label>select image : </label>
        <input type="file" id="image" accept="image/*"><br><br>
        <button type="button" onclick="upload()">Upload</button>
    </form>
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
<script type="text/javascript" src="js/uploadimage.js"></script>
</body>
</html>