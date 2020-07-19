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

// Reference database
var databaseRef = firebase.database().ref();
var countHadir = 0;
var countTelat = 0;
var countIzin = 0;

// Hitung Jumlah Karyawan
databaseRef.child('Karyawan').once('value', function (snapshot) {
  // body...
  console.log("There are "+snapshot.numChildren()+" karyawan");
  document.getElementById('jmlhKaryawan').innerHTML = snapshot.numChildren();
});

// Hitung Jumlah Kehadiran
databaseRef.child('Kehadiran').once('value', function (snapshot) {
  snapshot.forEach(function (childSnapshot) {
    // body...
    var childKey = childSnapshot.key;

    databaseRef.child('Kehadiran').child(childKey).once('value', function (snapshot) {
      snapshot.forEach(function (childSnapshot) {
        // body...
        var childKey2 = childSnapshot.key;

        databaseRef.child('Kehadiran').child(childKey).child(childKey2).once('value', function (snapshot) {
          snapshot.forEach(function (childSnapshot) {
            // body...
            countHadir = countHadir + childSnapshot.numChildren();
            console.log("There are "+countHadir+" kehadiran");
            document.getElementById('jmlhKehadiran').innerHTML = countHadir;
          });
        });
      });
    });
  });
});

// Hitung Jumlah Keterlambatan
databaseRef.child('Kehadiran').once('value', function (snapshot) {
  snapshot.forEach(function (childSnapshot) {
    // body...
    var childKey = childSnapshot.key;

    databaseRef.child('Kehadiran').child(childKey).once('value', function (snapshot) {
      snapshot.forEach(function (childSnapshot) {
        // body...
        var childKey2 = childSnapshot.key;

        databaseRef.child('Kehadiran').child(childKey).child(childKey2).once('value', function (snapshot) {
          snapshot.forEach(function (childSnapshot) {
            // body...
            var childKey3 = childSnapshot.key;

            databaseRef.child('Kehadiran').child(childKey).child(childKey2).child(childKey3).orderByChild('statusDatang').equalTo('Terlambat').once('value', function (snapshot) {
            // body...
            countTelat = countTelat + snapshot.numChildren();
            console.log("There are "+countTelat+" keterlambatan");
            document.getElementById('jmlhTerlambat').innerHTML = countTelat;
            });
          });
        });
      });
    });
  });
});

// Hitung Jumlah Izin
databaseRef.child('Perizinan').child('Karyawan').once('value', function (snapshot) {
  snapshot.forEach(function (childSnapshot) {
    // body...
    var childKey = childSnapshot.key;
    
    databaseRef.child('Perizinan').child('Karyawan').child(childKey).once('value', function (snapshot) {
    // body...
    countIzin = countIzin + snapshot.numChildren();
    console.log("There are "+countIzin+" izin");
    document.getElementById('jmlhIzin').innerHTML = countIzin;
    });
  });
});