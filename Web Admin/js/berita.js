function deleteData(childKey) {
  // body...
  var key = document.getElementById(deleteBtn);
  if (confirm("Are you sure?")) {
    firebase.database().ref().child('Pengumuman/'+ childKey).remove();
    alert('Data Berhasil Dihapus');
    window.location.reload();
  }
}