package com.example.trasstarea.Data;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import listaTareas.DaoTarea;
import listaTareas.Tarea;

@Database(entities = {Tarea.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoTarea daoTarea();
}
