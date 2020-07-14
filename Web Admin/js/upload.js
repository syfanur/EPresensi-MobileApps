function insert() {
    var name=document.getElementById("name").value;
    var pass=document.getElementById("pass").value;
    var posisi=document.getElementById("posisi").value;
    var ipendidikan=document.getElementById("pendidikan").value;
    var ialamat=document.getElementById("alamat").value;
    var igoldar=document.getElementById("goldar").value;
    var iagama=document.getElementById("agama").value;
    var inpwp=document.getElementById("npwp").value;
    var ibaju=document.getElementById("baju").value;
    var icelana=document.getElementById("celana").value;
    var isepatu=document.getElementById("sepatu").value;
    var ihp=document.getElementById("hp").value;
    var ihpkeluarga=document.getElementById("hpkeluarga").value;
    var inama=document.getElementById("nama").value;
   

    firebase.database().ref('Karyawan/'+name).set({
       npp: name,
        password: pass,
        posisi: posisi,
        pendidikan: ipendidikan,
        alamat: ialamat,
        goldar: igoldar,
        agama: iagama,
        npwp: inpwp,
        ukuran_baju: ibaju,
        ukuran_celana: icelana,
        ukuran_sepatu: isepatu,
        no_hp: ihp,
        no_hpkeluarga: ihpkeluarga,
        nama: inama

        
    });
  
    alert('The user is created successfully!');
   
}

