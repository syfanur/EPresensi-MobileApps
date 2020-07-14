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
let password = document.getElementById("password");
let nama = document.getElementById("nama");
let posisi=document.getElementById("posisi");
let ipendidikan=document.getElementById("pendidikan");
let ialamat=document.getElementById("alamat");
let igoldar=document.getElementById("goldar");
let iagama=document.getElementById("agama");
let inpwp=document.getElementById("npwp");
let ibaju=document.getElementById("baju");
let icelana=document.getElementById("celana");
let isepatu=document.getElementById("sepatu");
let ihp=document.getElementById("hp");
let ihpkeluarga=document.getElementById("hpkeluarga");


preview.addEventListener("submit", e => {
  e.preventDefault();

  if (!npp.value || !password.value || !nama.value || !posisi.value || !ipendidikan.value
    || !ialamat.value || !igoldar.value || !iagama.value || !inpwp.value || !ibaju.value || !icelana.value
    || !isepatu.value || !ihp.value || !ihpkeluarga.value)
    return null;

  db.ref("Karyawan/" + npp.value).set({
    npp: npp.value,
    password: password.value,
    nama: nama.value,
    posisi: posisi.value,
    pendidikan: ipendidikan.value,
    alamat: ialamat.value,
    goldar: igoldar.value,
    agama: iagama.value,
    npwp: inpwp.value,
    ukuran_baju: ibaju.value,
    ukuran_celana: icelana.value,
    ukuran_sepatu: isepatu.value,
    no_hp: ihp.value,
    no_hpkeluarga: ihpkeluarga.value
  });

  npp.value = "";
  nama.value = "";
  password.value = "";
  posisi.value = "";
  ipendidikan.value = "";
  ialamat.value = "";
  igoldar.value = "";
  iagama.value = "";
  inpwp.value = "";
  ibaju.value = "";
  icelana.value = "";
  isepatu.value = "";
  ihp.value = "";
  ihpkeluarga.value = "";
});

// READ
let list = document.getElementById("list-of-posts");
let listRef = db.ref("/Karyawan");

listRef.on("child_added", data => {
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

  let div = document.createElement("div");
  div.id = npp;
  div.setAttribute("class", "column");
  div.innerHTML = card(npp, password, nama, posisi, ipendidikan, ialamat, igoldar
    , iagama, inpwp, ibaju, icelana, isepatu, ihp, ihpkeluarga);
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

function card(npp, password, nama, posisi, ipendidikan, ialamat, igoldar
  , iagama, inpwp, ibaju, icelana, isepatu, ihp, ihpkeluarga) {
  return `
 
  <div class="card-body">
  <table class="table table-bordered" width="100%" cellspacing="0">
               <thead>
                 <tr>
                   <th>NPP</th>
                   <th>NamaLengkap</th>
                   <th>Password</th>
                   <th>Posisi</th>
                   <th>Alamat</th>
                   <th>Pendidikan</th>
                   <th>Action</th>
                 </tr>
               </thead>
               <tbody>
               <tr>
                 <td id="data-npp">${npp}</td>
                 <td id="data-nama">${nama}</td>
                 <td id="data-password">${password}</td>
                 <td id="data-posisi">${posisi}</td>
                 <td id="data-pendidikan">${ipendidikan}</td>
                 <td id="data-alamat">${ialamat}</td>
                 <td id="data-goldar" hidden>${igoldar}</td>
                 <td id="data-agama" hidden>${iagama}</td>
                 <td id="data-npwp" hidden>${inpwp}</td>
                 <td id="data-baju" hidden>${ibaju}</td>
                 <td id="data-celana" hidden>${icelana}</td>
                 <td id="data-sepatu" hidden>${isepatu}</td>
                 <td id="data-hp" hidden>${ihp}</td>
                 <td id="data-hpkeluarga" hidden>${ihpkeluarga}</td>
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
