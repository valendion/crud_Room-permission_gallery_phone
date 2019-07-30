package com.example.kontak_app;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tkontak")
public class Kontak implements Serializable{

    @PrimaryKey(autoGenerate = true)
    public  int kontakid;

    @ColumnInfo(name = "namaDpn_kontak")
    public String namadpnKontak;

    @ColumnInfo(name = "namaBlakang_kontak")
    public String namablakangKontak;

    @ColumnInfo(name = "noHp_kontak")
    public String nohpKontak;

    @ColumnInfo(name = "alamat_kontak")
    public  String alamatKontak;


    @ColumnInfo(name = "gambar_kontak")
    public  String gambarKontak;


    public int getKontakid() {
        return kontakid;
    }

    public void setKontakid(int kontakid) {
        this.kontakid = kontakid;
    }

    public String getNamadpnKontak() {
        return namadpnKontak;
    }

    public void setNamadpnKontak(String namadpnKontak) {
        this.namadpnKontak = namadpnKontak;
    }

    public String getNamablakangKontak() {
        return namablakangKontak;
    }

    public void setNamablakangKontak(String namablakangKontak) {
        this.namablakangKontak = namablakangKontak;
    }

    public String getNohpKontak() {
        return nohpKontak;
    }

    public void setNohpKontak(String nohpKontak) {
        this.nohpKontak = nohpKontak;
    }

    public String getAlamatKontak() {
        return alamatKontak;
    }

    public void setAlamatKontak(String alamatKontak) {
        this.alamatKontak = alamatKontak;
    }

    public String getGambarKontak() {
        return gambarKontak;
    }

    public void setGambarKontak(String gambarKontak) {
        this.gambarKontak = gambarKontak;
    }
}