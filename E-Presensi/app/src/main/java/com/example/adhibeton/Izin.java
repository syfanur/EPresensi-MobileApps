package com.example.adhibeton;

import java.io.Serializable;

public class Izin {
    private String Id, Jenis, Start, End, Bukti, Keterangan, Hari, Jam, Status;

    public Izin() {

    }

    public Izin(String id, String jenis, String start, String end, String bukti,
                String jam, String keterangan, String hari, String status ) {
        Id = id;
        Jenis = jenis;
        Start = start;
        End = end;
        Bukti = bukti;
        Keterangan = keterangan;
        Hari = hari;
        Status = status;
        Jam = jam;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getJenis() {
        return Jenis;
    }

    public void setJenis(String jenis) {
        Jenis = jenis;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getBukti() {
        return Bukti;
    }

    public void setBukti(String bukti) {
        Bukti = bukti;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getHari() {
        return Hari;
    }

    public void setHari(String hari) {
        Hari = hari;
    }

    public String getJam() {
        return Jam;
    }

    public void setJam(String jam) {
        Jam = jam;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}