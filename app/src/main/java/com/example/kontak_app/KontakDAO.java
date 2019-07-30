package com.example.kontak_app;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface KontakDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertKkontak(Kontak kontak);



    @Update
    int updateKontak (Kontak kontak);

    @Delete
    int deleteKontak (Kontak kontak);

    @Query("SELECT * FROM tkontak")
    Kontak[] selectAllKontaks();


    @Query("SELECT * FROM tkontak WHERE kontakid = :id LIMIT 1")
    Kontak selectKontakDetail(int id);
}
