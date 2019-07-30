package com.example.kontak_app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Kontak.class}, version = 1, exportSchema = false)
public  abstract class Appdatabase extends RoomDatabase{
    public abstract KontakDAO kontakDAO();
}