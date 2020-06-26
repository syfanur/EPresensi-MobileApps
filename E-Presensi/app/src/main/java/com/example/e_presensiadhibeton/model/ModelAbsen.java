package com.example.e_presensiadhibeton.model;

public class ModelAbsen {

    public String tanggal, waktu, absen, status, keterangan;

    public ModelAbsen() {

    }

    public ModelAbsen(String tanggal, String waktu, String absen, String status, String keterangan) {
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.absen = absen;
        this.status = status;
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getAbsen() {
        return absen;
    }

    public void setAbsen(String absen) {
        this.absen = absen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
