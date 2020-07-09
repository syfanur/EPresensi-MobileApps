package com.example.adhibeton;

public class ModelAbsenKehadiran {
    public String tanggal, waktuDatang, waktuPulang, absenDatang, absenPulang, statusDatang, statusPulang, keterangan, lokasi,tahun,bulan;


    public ModelAbsenKehadiran() {
    }

    public ModelAbsenKehadiran(String tanggal, String waktuDatang, String waktuPulang, String absenDatang, String absenPulang, String statusDatang, String statusPulang, String keterangan, String lokasi, String tahun, String bulan) {
        this.tanggal = tanggal;
        this.waktuDatang = waktuDatang;
        this.waktuPulang = waktuPulang;
        this.absenDatang = absenDatang;
        this.absenPulang = absenPulang;
        this.statusDatang = statusDatang;
        this.statusPulang = statusPulang;
        this.keterangan = keterangan;
        this.lokasi = lokasi;
        this.tahun = tahun;
        this.bulan = bulan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktuDatang() {
        return waktuDatang;
    }

    public void setWaktuDatang(String waktuDatang) {
        this.waktuDatang = waktuDatang;
    }

    public String getWaktuPulang() {
        return waktuPulang;
    }

    public void setWaktuPulang(String waktuPulang) {
        this.waktuPulang = waktuPulang;
    }

    public String getAbsenDatang() {
        return absenDatang;
    }

    public void setAbsenDatang(String absenDatang) {
        this.absenDatang = absenDatang;
    }

    public String getAbsenPulang() {
        return absenPulang;
    }

    public void setAbsenPulang(String absenPulang) {
        this.absenPulang = absenPulang;
    }

    public String getStatusDatang() {
        return statusDatang;
    }

    public void setStatusDatang(String statusDatang) {
        this.statusDatang = statusDatang;
    }

    public String getStatusPulang() {
        return statusPulang;
    }

    public void setStatusPulang(String statusPulang) {
        this.statusPulang = statusPulang;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }
}