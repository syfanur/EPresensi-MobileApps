package com.example.e_presensiadhibeton.model;

public class ModelAbsen {

    public String waktu, status, keterangan, tanggal;

    public ModelAbsen() {

    }

    public ModelAbsen(String waktu, String status, String keterangan, String tanggal) {
        this.waktu = waktu;
        this.status = status;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
