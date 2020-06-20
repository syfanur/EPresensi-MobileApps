package com.example.adhibeton;

import java.io.Serializable;

public class Izin {
    private String jenis, tanggalmulai, tanggalakhir, bukti, keterangan;

    public Izin() {

    }

    public Izin(String jenis, String tanggalmulai, String tanggalakhir, String bukti, String keterangan) {
        this.jenis = jenis;
        this.tanggalmulai = tanggalmulai;
        this.tanggalakhir = tanggalakhir;
        this.bukti = bukti;
        this.keterangan = keterangan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTanggalmulai() {
        return tanggalmulai;
    }

    public void setTanggalmulai(String tanggalmulai) {
        this.tanggalmulai = tanggalmulai;
    }

    public String getTanggalakhir() {
        return tanggalakhir;
    }

    public void setTanggalakhir(String tanggalakhir) {
        this.tanggalakhir = tanggalakhir;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}