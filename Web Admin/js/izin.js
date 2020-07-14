let firebaseConfig = {
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
let db = firebase.database();

//Create
let preview = document.getElementById("preview");
let npp = document.getElementById("npp");
let pengajuan = document.getElementById("tanggal");
let start = document.getElementById("start");
let end=document.getElementById("end");
let keterangan=document.getElementById("keterangan");
let jenis=document.getElementById("jenis");
let bukti=document.getElementById("bukti");


preview.addEventListener("submit", e => {
  e.preventDefault();

  if (!npp.value || !pengajuan.value || !start.value || !end.value 
    || !keterangan.value
    || !jenis.value || !bukti.value)
    return null;

  db.ref("Tes/" + "Karyawan/" + npp.preventDefault + pengajuan.value).set({

    pengajuan: pengajuan.value,
    npp: npp.value,
    jenis: jenis.value,
    start: start.value,
    end: end.value,
    keterangan: keterangan.value,
    bukti: bukti.value,
  });

  npp.value = "";
  pengajuan.value = "";
  jenis.value = "";
  start.value = "";
  end.value = "";
  keterangan.value = "";
  bukti.value = "";
  
});

// READ
let list = document.getElementById("list-of-posts");
let listRef = db.ref("Tes/" + "Karyawan/" + npp.value);

listRef.on("child_added", data => {
  let pengajuan = data.key;
  let npp = data.val().npp;
  let jenis = data.val().jenis;
  let start= data.val().start;
  let end=data.val().end;
  let keterangan=data.val().keterangan;
  let bukti=data.val().bukti;


  let div = document.createElement("div");
  div.id = pengajuan;
  div.setAttribute("class", "column");
  div.innerHTML = card(npp, jenis, start, pengajuan, end, keterangan, bukti);
  list.appendChild(div);
});

list.addEventListener("click", e => {
  let listNode = e.target.parentNode.parentNode;

  // UPDATE
  if (e.target.id == "edit") {
    npp.value = listNode.querySelector("#data-npp").innerText;
    password.value = listNode.querySelector("#data-password").innerText;
    nama.value = listNode.querySelector("#data-nama").innerText;
    posisi.value = listNode.querySelector("#data-posisi").innerText;
    ipendidikan.value = listNode.querySelector("#data-pendidikan").innerText;
    ialamat.value = listNode.querySelector("#data-alamat").innerText;
    igoldar.value = listNode.querySelector("#data-goldar").innerText;
    iagama.value = listNode.querySelector("#data-agama").innerText;
    inpwp.value = listNode.querySelector("#data-npwp").innerText;
    ibaju.value = listNode.querySelector("#data-baju").innerText;
    icelana.value = listNode.querySelector("#data-celana").innerText;
    isepatu.value = listNode.querySelector("#data-sepatu").innerText;
    ihpkeluarga.value = listNode.querySelector("#data-hpkeluarga").innerText;
    ihp.value = listNode.querySelector("#data-hp").innerText;
    
  }

  // DELETE
  if (e.target.id == "delete") {
    if (confirm("Are you sure?")) {
      let id = listNode.querySelector("#data-npp").innerText;
      db.ref("Karyawan/" + id).remove();
    }
  }
});

listRef.on("child_changed", data => {
  let npp = data.key;
  let password = data.val().password;
  let nama = data.val().nama;
  let posisi= data.val().posisi;
  let ipendidikan=data.val().pendidikan;
  let ialamat=data.val().alamat;
  let igoldar=data.val().goldar;
let iagama=data.val().agama;
let inpwp=data.val().npwp;
let ibaju=data.val().ukuran_baju;
let icelana=data.val().ukuran_celana;
let isepatu=data.val().ukuran_sepatu;
let ihp=data.val().no_hp;
let ihpkeluarga=data.val().no_hpkeluarga;

  let listNode = document.getElementById(npp);
  listNode.innerHTML = card(npp, password, nama, posisi, ipendidikan, ialamat, igoldar
    , iagama, inpwp, ibaju, icelana, isepatu, ihp, ihpkeluarga);
});

listRef.on("child_removed", data => {
  let listNode = document.getElementById(data.key);
  listNode.parentNode.removeChild(listNode);
});

function card(npp, jenis, start, pengajuan, end, keterangan, bukti) {
  return `
 
  <div class="card-body">
  <table class="table table-bordered" width="100%" cellspacing="0">
               <thead>
                 <tr>
                   <th>NPP</th>
                   <th>Tanggal Pengajuan</th>
                   <th>Jenis Izin</th>
                   <th>Tanggal Mulai Izin</th>
                   <th>Tanggal Akhir Izin</th>
                   <th>Keterangan</th>
                   <th>Bukti Izin</th>
                   <th>Action</th>
                 </tr>
               </thead>
               <tbody>
               <tr>
                 <td id="data-npp">${npp}</td>
                 <td id="data-pengajuan">${pengajuan}</td>
                 <td id="data-jenis">${jenis}</td>
                 <td id="data-start">${start}</td>
                 <td id="data-end">${end}</td>
                 <td id="data-keterangan">${keterangan}</td>
                 <td id="data-bukti">${bukti}</td>
                 <td>
                 <a class="card-footer-item" href="#preview" id="edit">Edit</a>
      <a class="card-footer-item" style="color:red" id="delete">Delete</a>
     </td>
               </tr>
               </tbody>
               
             </table>
             
 </div>
  `;
}
