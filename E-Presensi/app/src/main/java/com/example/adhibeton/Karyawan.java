package com.example.adhibeton;

public class Karyawan {
    private String alamat, goldar, nama, no_hp, no_hpkeluarga, npp, npwp, pendidikan, posisi, password,
            ukuran_baju, ukuran_celana, ukuran_sepatu, profil, agama;

    public Karyawan() {

    }

    public Karyawan(String alamat, String goldar, String nama, String no_hp,
                    String no_hpkeluarga, String npp, String npwp, String pendidikan, String posisi,
                    String password, String ukuran_baju, String ukuran_celana, String ukuran_sepatu,
                    String profil, String agama) {
        this.alamat = alamat;
        this.goldar = goldar;
        this.nama = nama;
        this.no_hp = no_hp;
        this.no_hpkeluarga = no_hpkeluarga;
        this.npp = npp;
        this.npwp = npwp;
        this.pendidikan = pendidikan;
        this.posisi = posisi;
        this.password = password;
        this.ukuran_baju = ukuran_baju;
        this.ukuran_celana = ukuran_celana;
        this.ukuran_sepatu = ukuran_sepatu;
        this.profil = profil;
        this.agama = agama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getGoldar() {
        return goldar;
    }

    public void setGoldar(String goldar) {
        this.goldar = goldar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getNo_hpkeluarga() {
        return no_hpkeluarga;
    }

    public void setNo_hpkeluarga(String no_hpkeluarga) {
        this.no_hpkeluarga = no_hpkeluarga;
    }

    public String getNpp() {
        return npp;
    }

    public void setNpp(String npp) {
        this.npp = npp;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUkuran_baju() {
        return ukuran_baju;
    }

    public void setUkuran_baju(String ukuran_baju) {
        this.ukuran_baju = ukuran_baju;
    }

    public String getUkuran_celana() {
        return ukuran_celana;
    }

    public void setUkuran_celana(String ukuran_celana) {
        this.ukuran_celana = ukuran_celana;
    }

    public String getUkuran_sepatu() {
        return ukuran_sepatu;
    }

    public void setUkuran_sepatu(String ukuran_sepatu) {
        this.ukuran_sepatu = ukuran_sepatu;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }
}
