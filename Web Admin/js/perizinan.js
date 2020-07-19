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

  function editData(childKey,childKey2) {
    // body...
    var key = document.getElementById(editBtn);
    var databaseRef = firebase.database().ref('Perizinan');
      databaseRef.child('Karyawan').child(childKey).child(childKey2).once('value', function (snapshot) {
        // body...
        console.log(snapshot.val());
        document.getElementById('inputNpp').value = childKey;
        document.getElementById('inputTanggal').value = snapshot.child('Id').val();
        document.getElementById('inputJenis').value = snapshot.child('Jenis').val();
        document.getElementById('inputImage').value = snapshot.child('Bukti').val();
        document.getElementById('inputMulai').value = snapshot.child('Start').val();
        document.getElementById('inputAkhir').value = snapshot.child('End').val();
        document.getElementById('inputKeterangan').value = snapshot.child('Keterangan').val();
        // snapshot.forEach(function(data) {
        // console.log(data.key);
        // });
      });
    //   $(function () {
    // // body...
    var data = [];
    var ref = firebase.database().ref('Perizinan').child('Karyawan').child(childKey).child(childKey2);
    ref.once('value', function (snapshot) {
      // body...
      console.log(snapshot.val());
      var childData = snapshot.val();
    

    $('#myForm').submit(function (event) {
      // body...

      var vJenis = $('#inputJenis').val();
      console.log(vJenis);

      var vImage = $('#inputImage').val();
      console.log(vImage);

      var vMulai = $('#inputMulai').val();
      console.log(vMulai);

      var vAkhir = $('#inputAkhir').val();
      console.log(vAkhir);

      var vKeterangan = $('#inputKeterangan').val();
      console.log(vKeterangan);

      var formIzin = {
        "Jenis": vJenis,
        "Bukti": vImage,
        "Start": vMulai,
        "End": vAkhir,
        "Keterangan": vKeterangan,
        "Hari": childData.Hari,
        "Id": childData.Id,
        "Jam": childData.Jam,
        "Status": childData.Status,
      }

      data.push(formIzin);
      console.log(data);

      ref.set(data, function (err) {
        // body...
        if (err) {
          alert("Data no go")
        }
      });
      window.location.reload();
      return false;
    })
    })
  // })
      $('#myModal').modal('show');

  }

  function deleteData(childKey,childKey2) {
    // body...
    var key = document.getElementById(deleteBtn);
    if (confirm("Are you sure?")) {
      firebase.database().ref().child('Perizinan/'+ 'Karyawan/' + childKey + '/' + childKey2).remove();
      alert('Data Berhasil Dihapus');
      window.location.reload();
    }
  }