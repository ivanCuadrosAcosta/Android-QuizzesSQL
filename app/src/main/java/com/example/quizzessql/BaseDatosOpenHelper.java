package com.example.quizzessql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatosOpenHelper extends SQLiteOpenHelper
{
    public BaseDatosOpenHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(context,nombre,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table preguntas(numPregunta integer primary key, enunciado text, pregunta text, opcion1 text, opcion2 text, opcion3 text, opcion4 text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists preguntas");
        db.execSQL("create table preguntas(numPregunta integer primary key, enunciado text, pregunta text, opcion1 text, opcion2 text, opcion3 text, opcion4 text)");
    }


}
