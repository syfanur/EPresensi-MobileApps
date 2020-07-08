package com.example.adhibeton;

public class ModelKehadiran {
    String absen, keterangan, lokasi, status, tanggal, waktu, bulan, tahun;

    public ModelKehadiran() {
    }

    public ModelKehadiran(String absen, String keterangan, String lokasi, String status, String tanggal, String waktu, String bulan, String tahun) {
        this.absen = absen;
        this.keterangan = keterangan;
        this.lokasi = lokasi;
        this.status = status;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.bulan = bulan;
        this.tahun = tahun;
    }

    public String getAbsen() {
        return absen;
    }

    public void setAbsen(String absen) {
        this.absen = absen;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
}