package com.example.adhibeton;

import java.io.Serializable;

public class Izin {
    private String id, jenis, tanggalmulai, tanggalakhir, bukti, keterangan, hari, status;

    public Izin() {

    }

    public Izin(String id, String jenis, String tanggalmulai, String tanggalakhir, String bukti, String keterangan, String hari, String status ) {
        this.id = id;
        this.jenis = jenis;
        this.tanggalmulai = tanggalmulai;
        this.tanggalakhir = tanggalakhir;
        this.bukti = bukti;
        this.keterangan = keterangan;
        this.hari = hari;
        this.status = status;
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

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}