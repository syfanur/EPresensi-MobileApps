function loginUser() {
    var email=document.getElementById("u_email").value;
    var password=document.getElementById("u_password").value;
    firebase.auth().signInWithEmailAndPassword(email,password).then(function () {

        window.location="index.html";
    }).catch(function (error) {
       //this will handle error
        var errorMessage=error.message;
        alert(errorMessage);
    });
}
